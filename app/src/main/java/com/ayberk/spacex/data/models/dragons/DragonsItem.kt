package com.ayberk.spacex.data.models.dragons

data class DragonsItem(
    val active: Boolean,
    val crew_capacity: Int,
    val description: String,
    val diameter: com.ayberk.spacex.data.models.dragons.Diameter,
    val dry_mass_kg: Int,
    val dry_mass_lb: Int,
    val first_flight: String,
    val flickr_images: List<String>,
    val heat_shield: com.ayberk.spacex.data.models.dragons.HeatShield,
    val height_w_trunk: com.ayberk.spacex.data.models.dragons.HeightWTrunk,
    val id: String,
    val launch_payload_mass: com.ayberk.spacex.data.models.dragons.LaunchPayloadMass,
    val launch_payload_vol: com.ayberk.spacex.data.models.dragons.LaunchPayloadVol,
    val name: String,
    val orbit_duration_yr: Int,
    val pressurized_capsule: com.ayberk.spacex.data.models.dragons.PressurizedCapsule,
    val return_payload_mass: com.ayberk.spacex.data.models.dragons.ReturnPayloadMass,
    val return_payload_vol: com.ayberk.spacex.data.models.dragons.ReturnPayloadVol,
    val sidewall_angle_deg: Int,
    val thrusters: List<com.ayberk.spacex.data.models.dragons.Thruster>,
    val trunk: com.ayberk.spacex.data.models.dragons.Trunk,
    val type: String,
    val wikipedia: String
)