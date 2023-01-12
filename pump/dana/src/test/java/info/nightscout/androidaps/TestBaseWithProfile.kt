package info.nightscout.androidaps

import dagger.android.AndroidInjector
import dagger.android.HasAndroidInjector
import info.nightscout.core.utils.fabric.FabricPrivacy
import info.nightscout.interfaces.Config
import info.nightscout.interfaces.plugin.ActivePlugin
import info.nightscout.interfaces.profile.DefaultValueHelper
import info.nightscout.interfaces.profile.ProfileFunction
import info.nightscout.interfaces.profile.ProfileStore
import info.nightscout.rx.bus.RxBus
import info.nightscout.shared.interfaces.ResourceHelper
import info.nightscout.shared.utils.DateUtil
import org.json.JSONObject
import org.junit.jupiter.api.BeforeEach
import org.mockito.Mock

@Suppress("SpellCheckingInspection")
open class TestBaseWithProfile : TestBase() {

    @Mock lateinit var activePluginProvider: ActivePlugin
    @Mock lateinit var rh: ResourceHelper
    @Mock lateinit var fabricPrivacy: FabricPrivacy
    @Mock lateinit var profileFunction: ProfileFunction
    @Mock lateinit var defaultValueHelper: DefaultValueHelper
    @Mock lateinit var dateUtil: DateUtil
    @Mock lateinit var config: Config

    val rxBus = RxBus(aapsSchedulers, aapsLogger)

    val profileInjector = HasAndroidInjector {
        AndroidInjector {
        }
    }

    private lateinit var validProfileJSON: String
    @Suppress("PropertyName") val TESTPROFILENAME = "someProfile"

    @BeforeEach
    fun prepareMock() {
        validProfileJSON = "{\"dia\":\"5\",\"carbratio\":[{\"time\":\"00:00\",\"value\":\"30\"}],\"carbs_hr\":\"20\",\"delay\":\"20\",\"sens\":[{\"time\":\"00:00\",\"value\":\"3\"}," +
            "{\"time\":\"2:00\",\"value\":\"3.4\"}],\"timezone\":\"UTC\",\"basal\":[{\"time\":\"00:00\",\"value\":\"1\"}],\"target_low\":[{\"time\":\"00:00\",\"value\":\"4.5\"}]," +
            "\"target_high\":[{\"time\":\"00:00\",\"value\":\"7\"}],\"startDate\":\"1970-01-01T00:00:00.000Z\",\"units\":\"mmol\"}"
    }

    fun getValidProfileStore(): ProfileStore {
        validProfileJSON = "{\"dia\":\"5\",\"carbratio\":[{\"time\":\"00:00\",\"value\":\"30\"}],\"carbs_hr\":\"20\",\"delay\":\"20\",\"sens\":[{\"time\":\"00:00\",\"value\":\"3\"}," +
            "{\"time\":\"2:00\",\"value\":\"3.4\"}],\"timezone\":\"UTC\",\"basal\":[{\"time\":\"00:00\",\"value\":\"1\"}],\"target_low\":[{\"time\":\"00:00\",\"value\":\"4.5\"}]," +
            "\"target_high\":[{\"time\":\"00:00\",\"value\":\"7\"}],\"startDate\":\"1970-01-01T00:00:00.000Z\",\"units\":\"mmol\"}"

        val json = JSONObject()
        val store = JSONObject()
        store.put(TESTPROFILENAME, JSONObject(validProfileJSON))
        json.put("defaultProfile", TESTPROFILENAME)
        json.put("store", store)
        return ProfileStoreObject(profileInjector, json, dateUtil)
    }
}