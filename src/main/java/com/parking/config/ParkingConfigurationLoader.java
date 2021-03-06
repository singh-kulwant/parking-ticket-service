package com.parking.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import com.pricing.models.FixedHourlyBilling;
import com.pricing.models.HourlyBilling;
import com.slot.models.HeavyElectricSlot;
import com.slot.models.LightElectricSlot;
import com.slot.models.StandardSlot;

/**
 * This class loads configuration properties related to toll parking such as
 * Parking Slot Capacity, various Fixed and Hourly Billing Rates and initializes
 * Parking Slot Instances
 *
 * @author root
 *
 */
@Configuration
@PropertySource("classpath:application.yml")
@ConfigurationProperties(prefix = "parking")
public class ParkingConfigurationLoader {

	@Value(value = "${standardCapacity:3}")
	public int standardCapacity;

	@Value(value = "${lightElectricCapacity:1}")
	public int lightElectricCapacity;

	@Value(value = "${heavyElectricCapacity:1}")
	public int heavyElectricCapacity;

	@Value(value = "${hourlyBillingRate:4}")
	public Long hourlyBillingRate;

	@Value(value = "${fixedHourlyBillingRate:2}")
	public Long fixedHourlyBillingRate;

	@Value(value = "${fixedHourlyFixedCharge:5}")
	public Long fixedHourlyFixedCharge;

	@Bean
	public void init() {

		/* Initializing Billing rates and fixed charges for various Pricing policies */
		HourlyBilling.rate = hourlyBillingRate;
		FixedHourlyBilling.rate = fixedHourlyBillingRate;
		FixedHourlyBilling.fixedCharge = fixedHourlyFixedCharge;

		/*
		 * Initializing instances of Parking slots, with parking slot capacity and
		 * Pricing policy
		 */
		StandardSlot.initStandardSlotInstance(standardCapacity, new HourlyBilling());
		LightElectricSlot.initLightElectricSlotInstance(lightElectricCapacity, new FixedHourlyBilling());
		HeavyElectricSlot.initHeavyElectricSlotInstance(heavyElectricCapacity, new FixedHourlyBilling());

	}

}
