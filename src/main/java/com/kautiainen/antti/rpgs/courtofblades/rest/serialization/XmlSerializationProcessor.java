package com.kautiainen.antti.rpgs.courtofblades.rest.serialization;

import org.restexpress.serialization.xml.XstreamXmlProcessor;

import com.kautiainen.antti.rpgs.courtofblades.model.Clock;
import com.kautiainen.antti.rpgs.courtofblades.model.Coterie;
import com.kautiainen.antti.rpgs.courtofblades.model.CoterieUpgrade;
import com.kautiainen.antti.rpgs.courtofblades.model.HouseModel;
import com.kautiainen.antti.rpgs.courtofblades.model.SpecialAbility;
import com.kautiainen.antti.rpgs.courtofblades.model.Track;


public class XmlSerializationProcessor
extends XstreamXmlProcessor
{
	public XmlSerializationProcessor()
    {
	    super();
        alias("coterie", Coterie.class);
        alias("house", HouseModel.class);
        alias("clock", Clock.class);
        alias("track", Track.class);
        alias("ability", SpecialAbility.class);
        alias("upgrade", CoterieUpgrade.class);
        
//		alias("element_name", Element.class);
//		alias("element_name", Element.class);
//		alias("element_name", Element.class);
//		alias("element_name", Element.class);
    }
}
