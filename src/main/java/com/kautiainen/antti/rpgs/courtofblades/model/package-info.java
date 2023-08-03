/**
 * Package containing the model used for Court of Blades
 * rpg data storage.
 * 
 * The generic utilty classes Named, and NamedAndDescribed
 * gathers up methods for storing names, and descriptions
 * into single class.
 * 
 * All playsheets share XpTrigger and SpecialAbility classes.
 * 
 * CoterieUpgradeSource implements a source of possible Coterie Upgrades.
 * Both CoterieType and HouseModel implements it, as both provide
 * Coterie Upgrades.
 * 
 * SpecialAbilityContainer-class implements shared methods
 * for all containers of SpecialAbilities.The SpecialAbilityContainer
 * may be mutable or immutable according to implementation.
 * 
 * HouseModel represents Houses, and act as a SpecialAbilityContainer
 * for additional experience trigger, the healer type, special abilites
 * and coterie upgrades for the coteries serving
 * the modelled house. The resource determines the fixed resource
 * the house has regardless the campaign.
 * 
 * Coterie represnets a Coterie serving some house. Each Coterie
 * has CoterieType and HouseModel which provides basic details of the
 * Coterie capabilities and default advancement options. Coterie also
 * tracks the reputation and influence of the coterie.
 * 
 */
package com.kautiainen.antti.rpgs.courtofblades.model;