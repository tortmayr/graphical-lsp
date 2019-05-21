/*******************************************************************************
 * Copyright (c) 2019 EclipseSource and others.
 *  
 *   This program and the accompanying materials are made available under the
 *   terms of the Eclipse Public License v. 2.0 which is available at
 *   http://www.eclipse.org/legal/epl-2.0.
 *  
 *   This Source Code may also be made available under the following Secondary
 *   Licenses when the conditions for such availability set forth in the Eclipse
 *   Public License v. 2.0 are satisfied: GNU General Public License, version 2
 *   with the GNU Classpath Exception which is available at
 *   https://www.gnu.org/software/classpath/license.html.
 *  
 *   SPDX-License-Identifier: EPL-2.0 OR GPL-2.0 WITH Classpath-exception-2.0
 ******************************************************************************/
package com.eclipsesource.glsp.graph.json;

import static com.eclipsesource.glsp.graph.GraphPackage.Literals.GCOMPARTMENT;
import static com.eclipsesource.glsp.graph.GraphPackage.Literals.GEDGE;
import static com.eclipsesource.glsp.graph.GraphPackage.Literals.GGRAPH;
import static com.eclipsesource.glsp.graph.GraphPackage.Literals.GLABEL;
import static com.eclipsesource.glsp.graph.GraphPackage.Literals.GNODE;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.emfjson.jackson.annotations.EcoreTypeInfo;
import org.emfjson.jackson.databind.EMFContext;
import org.emfjson.jackson.databind.deser.ReferenceEntry;
import org.emfjson.jackson.module.EMFModule;
import org.emfjson.jackson.resource.JsonResource;
import org.emfjson.jackson.utils.ValueReader;
import org.emfjson.jackson.utils.ValueWriter;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.SerializerProvider;

public class GGraphObjectMapperFactory {

	private static final String DEFAULT_TYPE_ATT = "type";

	private static final JsonSerializer<EObject> ID_REFERENCE_SERIALIZER = new JsonSerializer<EObject>() {
		@Override
		public void serialize(EObject v, JsonGenerator g, SerializerProvider s) throws IOException {
			g.writeString(((JsonResource) v.eResource()).getID(v));
		}
	};

	private static final JsonDeserializer<ReferenceEntry> ID_REFERENCE_DESERIALIZER = new JsonDeserializer<ReferenceEntry>() {
		@Override
		public ReferenceEntry deserialize(JsonParser parser, DeserializationContext ctxt) throws IOException {
			final EObject parent = EMFContext.getParent(ctxt);
			final EReference reference = EMFContext.getReference(ctxt);
			if (parser.getCurrentToken() == JsonToken.FIELD_NAME) {
				parser.nextToken();
			}
			return new ReferenceEntry.Base(parent, reference, parser.getText());
		}
	};

	private Map<String, EClass> typeMap = new HashMap<>();

	public GGraphObjectMapperFactory withDefaultTypeMap() {
		typeMap.put("graph", GGRAPH);
		typeMap.put("node", GNODE);
		typeMap.put("edge", GEDGE);
		typeMap.put("comp", GCOMPARTMENT);
		typeMap.put("label", GLABEL);
		return this;
	}

	public GGraphObjectMapperFactory withTypes(Map<String, EClass> types) {
		typeMap.putAll(types);
		return this;
	}

	public ObjectMapper build() {
		ObjectMapper mapper = new ObjectMapper();
		final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.ENGLISH);
		dateFormat.setTimeZone(TimeZone.getDefault());
		mapper.configure(SerializationFeature.INDENT_OUTPUT, true);
		mapper.setDateFormat(dateFormat);
		mapper.setTimeZone(TimeZone.getDefault());
		mapper.registerModule(createGModelModule());
		return mapper;
	}

	private EMFModule createGModelModule() {
		EMFModule module = new EMFModule();
		module.setTypeInfo(new EcoreTypeInfo(getTypeAttribute(), createValueReader(), createValueWriter()));
		module.setReferenceSerializer(createReferenceSerializer());
		module.setReferenceDeserializer(createReferenceDeserializer());
		return module;
	}

	protected JsonSerializer<EObject> createReferenceSerializer() {
		return ID_REFERENCE_SERIALIZER;
	}

	protected JsonDeserializer<ReferenceEntry> createReferenceDeserializer() {
		return ID_REFERENCE_DESERIALIZER;
	}

	protected String getTypeAttribute() {
		return DEFAULT_TYPE_ATT;
	}

	protected ValueReader<String, EClass> createValueReader() {
		return (value, context) -> {
			if (typeMap.containsKey(value)) {
				return typeMap.get(value);
			}
			return EcoreTypeInfo.defaultValueReader.readValue(value, context);
		};
	}

	protected ValueWriter<EClass, String> createValueWriter() {
		// TODO
		return EcoreTypeInfo.defaultValueWriter;
	}

}
