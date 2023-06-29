package patches.buildTypes

import jetbrains.buildServer.configs.kotlin.v2019_2.*
import jetbrains.buildServer.configs.kotlin.v2019_2.ui.*

/*
This patch script was generated by TeamCity on settings change in UI.
To apply the patch, change the buildType with id = 'Beta_UpgradeAlphaBeta'
accordingly, and delete the patch script.
*/
changeBuildType(RelativeId("Beta_UpgradeBetaRelease")) {
    check(name == "Upgrade - Beta -> Release") {
        "Unexpected name: '$name'"
    }
    name = "Upgrade - Beta -> Release"

    check(description == "Upgrades the current Beta to Release.") {
        "Unexpected description: '$description'"
    }
    description = "Upgrades the current Beta to Release."

    params {
        expect {
            param("Default.Branch", "version/%Current Minecraft Version%")
        }
        update {
            param("Default.Branch", "release/%Current Minecraft Version%")
        }
        expect {
            param("Target.Branch", "version")
        }
        update {
            param("Target.Branch", "release")
        }
        expect {
            param("VCS.Branches", "+:refs/heads/version/(*)")
        }
        update {
            param("VCS.Branches", "+:refs/heads/release/(*)")
        }
    }
}