rootProject.name = "hexagonal-multimodule-clean"
include("domain", "application")
include("adapter:inbound:controller")
include("adapter:outbound:repository")
include("infrastructure:jpa")
include("server:api")