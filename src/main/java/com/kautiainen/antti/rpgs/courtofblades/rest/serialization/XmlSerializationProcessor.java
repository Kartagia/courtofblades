package com.kautiainen.antti.solita.serialization;

import org.restexpress.serialization.xml.XstreamXmlProcessor;

import com.kautiainen.antti.solita.model.Station;

public class XmlSerializationProcessor
extends XstreamXmlProcessor
{
	public XmlSerializationProcessor()
    {
	    super();
        alias("station", Station.class);
//		alias("element_name", Element.class);
//		alias("element_name", Element.class);
//		alias("element_name", Element.class);
//		alias("element_name", Element.class);
    }
}
