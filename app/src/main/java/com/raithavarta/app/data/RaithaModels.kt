package com.raithavarta.app.data

import androidx.annotation.DrawableRes
import com.raithavarta.app.R

data class CropCategory(
    val id: String,
    val en: String,
    val kn: String,
    @DrawableRes val imageRes: Int
)

data class Tip(
    val id: String,
    val categoryId: String,
    val type: String,
    val titleEn: String,
    val titleKn: String,
    val shortAdviceEn: String,
    val shortAdviceKn: String,
    val detailEn: String,
    val detailKn: String,
    val weatherTag: String,
    val dosage: String,
    val precautions: List<String>,
    val expert: String,
    @DrawableRes val imageRes: Int
)

data class SuccessStory(
    val farmerName: String,
    val district: String,
    val crop: String,
    val result: String,
    val storyEn: String,
    val storyKn: String,
    @DrawableRes val imageRes: Int
)

data class ExpertAnalysis(
    val disease: String,
    val diseaseKn: String,
    val confidence: String,
    val severity: String,
    val treatmentSteps: List<String>,
    val prevention: List<String>
)

object RaithaData {
    private const val SOURCE_NKAFC = "Source: IMD NKAFC Dharwad agromet bulletin, 08 May 2026"
    private const val SOURCE_KARNATAKA = "Source: IMD Bengaluru agromet advisory PDF, May 2026"
    private const val SOURCE_KV = "Source: Krishi Vignana Kannada Quarterly, UAS Bengaluru, Volume 50 Issue 1, March 2026"

    val categories = listOf(
        CropCategory("all", "All", "ಎಲ್ಲಾ", R.drawable.kv_moringa),
        CropCategory("paddy", "Paddy", "ಭತ್ತ", R.drawable.paddy),
        CropCategory("coconut", "Coconut", "ತೆಂಗು", R.drawable.coconut),
        CropCategory("areca", "Areca Nut", "ಅಡಿಕೆ", R.drawable.areca_cut),
        CropCategory("tomato", "Tomato", "ಟೊಮ್ಯಾಟೊ", R.drawable.tomatoes),
        CropCategory("moringa", "Moringa", "ನುಗ್ಗೆ", R.drawable.kv_moringa),
        CropCategory("fertilizer", "Fertilizer", "ಗೊಬ್ಬರ", R.drawable.fertilizers),
        CropCategory("irrigation", "Irrigation", "ನೀರಾವರಿ", R.drawable.irrigation),
        CropCategory("pest", "Pest Control", "ಕೀಟ ನಿಯಂತ್ರಣ", R.drawable.pest_control),
        CropCategory("corn", "Maize/Corn", "ಮೆಕ್ಕೆಜೋಳ", R.drawable.corn)
    )

    val tips = listOf(
        Tip(
            id = "paddy-harvest-nkafc",
            categoryId = "paddy",
            type = "Harvesting",
            titleEn = "Harvest early transplanted paddy with a combine",
            titleKn = "ಮುಂಚಿತವಾಗಿ ನಾಟಿ ಮಾಡಿದ ಭತ್ತವನ್ನು ಕಾಂಬೈನ್ ಮೂಲಕ ಕೊಯ್ಲು ಮಾಡಿ",
            shortAdviceEn = "If early transplanted paddy has reached harvest stage, harvest with a combine harvester and dry produce well before storage.",
            shortAdviceKn = "ಮುಂಚಿತವಾಗಿ ನಾಟಿ ಮಾಡಿದ ಭತ್ತ ಕೊಯ್ಲಿನ ಹಂತದಲ್ಲಿದ್ದರೆ ಕಾಂಬೈನ್ ಹಾರ್ವೆಸ್ಟರ್ ಬಳಸಿ ಕೊಯ್ಲು ಮಾಡಿ ಮತ್ತು ಸಂಗ್ರಹಿಸುವ ಮೊದಲು ಚೆನ್ನಾಗಿ ಒಣಗಿಸಿ.",
            detailEn = "The North Karnataka agromet bulletin advises timely harvesting of matured paddy. Harvested produce should be sun dried before bagging, and paddy straw should be used for compost or vermicompost instead of burning.",
            detailKn = "ಉತ್ತರ ಕರ್ನಾಟಕ ಕೃಷಿ ಹವಾಮಾನ ಸಲಹೆಯಲ್ಲಿ ಪಕ್ವವಾದ ಭತ್ತವನ್ನು ಸಮಯಕ್ಕೆ ಕೊಯ್ಲು ಮಾಡಲು ತಿಳಿಸಲಾಗಿದೆ. ಚೀಲಗಳಲ್ಲಿ ತುಂಬುವ ಮೊದಲು ಉತ್ಪನ್ನವನ್ನು ಬಿಸಿಲಿನಲ್ಲಿ ಒಣಗಿಸಿ; ಭತ್ತದ ಹುಲ್ಲನ್ನು ಸುಡುವ ಬದಲು ಕಾಂಪೋಸ್ಟ್ ಅಥವಾ ವರ್ಮಿಕಾಂಪೋಸ್ಟ್ ಮಾಡಲು ಬಳಸಿ.",
            weatherTag = "No to light rain forecast",
            dosage = "Use combine harvester; sun dry grain before bagging",
            precautions = listOf(
                "Do not bag paddy while grain is moist.",
                "Keep harvested produce covered if thunderstorm clouds build up.",
                "Do not burn paddy straw; compost it for soil health."
            ),
            expert = SOURCE_NKAFC,
            imageRes = R.drawable.paddy
        ),
        Tip(
            id = "kv-rainwater-farm-pond",
            categoryId = "irrigation",
            type = "Water Harvesting",
            titleEn = "Store rainwater in farm ponds",
            titleKn = "ಕೃಷಿ ಹೊಂಡಗಳಲ್ಲಿ ಮಳೆ ನೀರು ಸಂಗ್ರಹಿಸಿ",
            shortAdviceEn = "Use farm ponds and rainwater harvesting structures to save monsoon water for dry weeks and high-value crops.",
            shortAdviceKn = "ಒಣ ವಾರಗಳು ಮತ್ತು ಮೌಲ್ಯಯುತ ಬೆಳೆಗಳಿಗೆ ಮಳೆಗಾಲದ ನೀರು ಉಳಿಸಲು ಕೃಷಿ ಹೊಂಡ ಮತ್ತು ಮಳೆ ನೀರು ಕೊಯ್ಲು ರಚನೆಗಳನ್ನು ಬಳಸಿ.",
            detailEn = "Krishi Vignana highlights a farmer success story built around rainwater harvesting. The article shows farm ponds used for storing rainwater and then supporting protected cultivation, flower crops and irrigation during dry periods.",
            detailKn = "ಕೃಷಿ ವಿಜ್ಞಾನ ಪತ್ರಿಕೆಯಲ್ಲಿ ಮಳೆ ನೀರು ಕೊಯ್ಲಿನ ಮೂಲಕ ಯಶಸ್ಸು ಕಂಡ ರೈತನ ಕಥೆಯನ್ನು ವಿವರಿಸಲಾಗಿದೆ. ಲೇಖನದಲ್ಲಿ ಕೃಷಿ ಹೊಂಡಗಳಲ್ಲಿ ಮಳೆ ನೀರು ಸಂಗ್ರಹಿಸಿ, ಸಂರಕ್ಷಿತ ಕೃಷಿ, ಹೂ ಬೆಳೆಗಳು ಮತ್ತು ಒಣ ಅವಧಿಯ ನೀರಾವರಿಗೆ ಬಳಸಿರುವುದನ್ನು ತೋರಿಸಲಾಗಿದೆ.",
            weatherTag = "Kannada PDF rainwater harvesting",
            dosage = "Build or maintain a farm pond as per land slope and local engineer advice",
            precautions = listOf(
                "Fence farm ponds and keep children away from the water edge.",
                "Desilt ponds before monsoon where needed.",
                "Use stored water crop-wise instead of flooding the field."
            ),
            expert = SOURCE_KV,
            imageRes = R.drawable.kv_rainwater
        ),
        Tip(
            id = "kv-integrated-vermicompost",
            categoryId = "fertilizer",
            type = "Integrated Farming",
            titleEn = "Use farm waste for vermicompost",
            titleKn = "ಕೃಷಿ ಅವಶೇಷಗಳಿಂದ ವರ್ಮಿಕಾಂಪೋಸ್ಟ್ ತಯಾರಿಸಿ",
            shortAdviceEn = "Convert dry leaves, crop waste and cattle shed waste into vermicompost for better soil health and lower fertilizer cost.",
            shortAdviceKn = "ಒಣ ಎಲೆ, ಬೆಳೆ ಅವಶೇಷ ಮತ್ತು ಪಶುಶಾಲೆಯ ತ್ಯಾಜ್ಯವನ್ನು ವರ್ಮಿಕಾಂಪೋಸ್ಟ್ ಆಗಿ ಮಾಡಿ ಮಣ್ಣಿನ ಆರೋಗ್ಯ ಹೆಚ್ಚಿಸಿ ಗೊಬ್ಬರ ವೆಚ್ಚ ಕಡಿಮೆ ಮಾಡಿ.",
            detailEn = "The Krishi Vignana farmer story shows integrated farming with a vermicompost unit. Composting farm residues closes the nutrient cycle and supports protected cultivation without depending only on purchased inputs.",
            detailKn = "ಕೃಷಿ ವಿಜ್ಞಾನ ರೈತರ ಕಥೆಯಲ್ಲಿ ವರ್ಮಿಕಾಂಪೋಸ್ಟ್ ಘಟಕದೊಂದಿಗೆ ಸಮಗ್ರ ಕೃಷಿಯನ್ನು ತೋರಿಸಲಾಗಿದೆ. ಕೃಷಿ ಅವಶೇಷಗಳನ್ನು ಕಾಂಪೋಸ್ಟ್ ಮಾಡುವುದರಿಂದ ಪೋಷಕಾಂಶ ಚಕ್ರ ಹೊಲದಲ್ಲೇ ಉಳಿದು, ಖರೀದಿ ಗೊಬ್ಬರದ ಅವಲಂಬನೆ ಕಡಿಮೆಯಾಗುತ್ತದೆ.",
            weatherTag = "Soil health advisory from Kannada PDF",
            dosage = "Use well-decomposed vermicompost; avoid fresh waste near roots",
            precautions = listOf(
                "Keep the vermicompost bed moist, not waterlogged.",
                "Protect the bed from direct sun and heavy rain.",
                "Apply only mature compost around crop roots."
            ),
            expert = SOURCE_KV,
            imageRes = R.drawable.kv_vermicompost
        ),
        Tip(
            id = "kv-moringa-agroforestry",
            categoryId = "moringa",
            type = "Agroforestry",
            titleEn = "Grow moringa in an agroforestry system",
            titleKn = "ಕೃಷಿ-ಅರಣ್ಯ ಪದ್ಧತಿಯಲ್ಲಿ ನುಗ್ಗೆ ಬೆಳೆಸಿ",
            shortAdviceEn = "Moringa can fit well on farm bunds or planned rows, giving leaf/pod income while keeping the farm green.",
            shortAdviceKn = "ನುಗ್ಗೆ ಮರವನ್ನು ಹೊಲದ ಬದುಗಳಲ್ಲಿ ಅಥವಾ ಯೋಜಿತ ಸಾಲುಗಳಲ್ಲಿ ಬೆಳೆಸಿದರೆ ಎಲೆ/ಕಾಯಿ ಆದಾಯದ ಜೊತೆಗೆ ಹೊಲ ಹಸಿರಾಗಿರುತ್ತದೆ.",
            detailEn = "The cover theme of Krishi Vignana March 2026 presents moringa under a krishi-aranya or agroforestry system. Use planned spacing, pruning and drainage so trees support the farm without shading the main crop too much.",
            detailKn = "ಕೃಷಿ ವಿಜ್ಞಾನ ಮಾರ್ಚ್ 2026 ಸಂಚಿಕೆಯ ಮುಖಪುಟ ವಿಷಯದಲ್ಲಿ ಕೃಷಿ-ಅರಣ್ಯ ಪದ್ಧತಿಯಲ್ಲಿ ನುಗ್ಗೆ ಬೆಳೆ ಕುರಿತು ತಿಳಿಸಲಾಗಿದೆ. ಮುಖ್ಯ ಬೆಳೆ ಮೇಲೆ ಹೆಚ್ಚು ನೆರಳು ಬೀಳದಂತೆ ಅಂತರ, ಕತ್ತರಿಸಿ ರೂಪಿಸುವಿಕೆ ಮತ್ತು ನೀರು ಹೊರಹಾಕುವ ವ್ಯವಸ್ಥೆಯನ್ನು ಯೋಜಿಸಿ.",
            weatherTag = "Krishi Vignana cover crop",
            dosage = "Plant on bunds or planned rows; prune regularly",
            precautions = listOf(
                "Do not let trees shade young field crops heavily.",
                "Keep drainage open around tree rows.",
                "Use healthy planting material from a reliable nursery."
            ),
            expert = SOURCE_KV,
            imageRes = R.drawable.kv_moringa
        ),
        Tip(
            id = "paddy-water-blast-stem-borer",
            categoryId = "paddy",
            type = "Water and Pest",
            titleEn = "Maintain shallow water in young paddy",
            titleKn = "ಇಳಿವಯಸ್ಸಿನ ಭತ್ತದಲ್ಲಿ ಕಡಿಮೆ ನೀರಿನ ಮಟ್ಟ ಕಾಯ್ದುಕೊಳ್ಳಿ",
            shortAdviceEn = "At transplanting or early vegetative stage, keep 2-3 cm water, avoid moisture stress and ensure drainage during light rains.",
            shortAdviceKn = "ನಾಟಿ ಅಥವಾ ಆರಂಭಿಕ ಬೆಳವಣಿಗೆ ಹಂತದಲ್ಲಿ 2-3 ಸೆಂ.ಮೀ. ನೀರು ಕಾಯ್ದುಕೊಳ್ಳಿ, ತೇವಾಂಶ ಕೊರತೆ ತಪ್ಪಿಸಿ ಮತ್ತು ಹಗುರ ಮಳೆಯಲ್ಲಿ ನೀರು ಹೊರಹೋಗುವಂತೆ ಮಾಡಿ.",
            detailEn = "The Chamarajanagara and Mysore/Mandya advisories mention stem borer, leaf folder and blast risk in young paddy. Use pheromone traps at 5 per acre, Chlorantraniliprole 0.4 ml/L for stem borer or leaf folder where advised, and Tricyclazole 0.6 g/L for blast after local confirmation.",
            detailKn = "ಚಾಮರಾಜನಗರ ಮತ್ತು ಮೈಸೂರು/ಮಂಡ್ಯ ಸಲಹೆಗಳಲ್ಲಿ ಇಳಿವಯಸ್ಸಿನ ಭತ್ತದಲ್ಲಿ ಕಾಂಡ ಕೊರೆತ, ಎಲೆ ಮಡಚು ಹುಳು ಮತ್ತು ಬ್ಲಾಸ್ಟ್ ಅಪಾಯವನ್ನು ಉಲ್ಲೇಖಿಸಲಾಗಿದೆ. ಎಕರೆಗೆ 5 ಫೆರೋಮೋನ್ ಟ್ರ್ಯಾಪ್‌ಗಳನ್ನು ಇಡಿ; ಸ್ಥಳೀಯ ಸಲಹೆ ಪಡೆದು ಕಾಂಡ ಕೊರೆತ/ಎಲೆ ಮಡಚು ಹುಳಿಗೆ ಕ್ಲೋರಾಂಟ್ರಾನಿಲಿಪ್ರೋಲ್ 0.4 ಮಿ.ಲೀ./ಲೀ. ಮತ್ತು ಬ್ಲಾಸ್ಟ್‌ಗೆ ಟ್ರೈಸೈಕ್ಲಜೋಲ್ 0.6 ಗ್ರಾಂ/ಲೀ. ಬಳಸಿ.",
            weatherTag = "Light rain and drainage advisory",
            dosage = "Pheromone traps 5/acre; Chlorantraniliprole 0.4 ml/L; Tricyclazole 0.6 g/L",
            precautions = listOf(
                "Use chemicals only after pest or disease symptoms are confirmed.",
                "Maintain drainage so standing water does not stress the crop.",
                "Spray in calm weather and follow local agriculture officer guidance."
            ),
            expert = SOURCE_KARNATAKA,
            imageRes = R.drawable.irrigation
        ),
        Tip(
            id = "tomato-fruit-borer-blight",
            categoryId = "tomato",
            type = "Disease and Pest",
            titleEn = "Tomato fruit borer and early blight watch",
            titleKn = "ಟೊಮ್ಯಾಟೊದಲ್ಲಿ ಹಣ್ಣು ಕೊರೆತ ಮತ್ತು ಆರಂಭಿಕ ಬ್ಲೈಟ್ ಗಮನಿಸಿ",
            shortAdviceEn = "Keep irrigation uniform to prevent fruit cracking, and watch for fruit borer and early blight during fruit development.",
            shortAdviceKn = "ಹಣ್ಣು ಅಭಿವೃದ್ಧಿ ಹಂತದಲ್ಲಿ ಹಣ್ಣು ಬಿರುಕು ತಪ್ಪಿಸಲು ಸಮ ನೀರಾವರಿ ಮಾಡಿ; ಹಣ್ಣು ಕೊರೆತ ಮತ್ತು ಆರಂಭಿಕ ಬ್ಲೈಟ್ ಲಕ್ಷಣಗಳನ್ನು ಗಮನಿಸಿ.",
            detailEn = "The Karnataka agromet advisory recommends Emamectin benzoate 0.4 g/L and Mancozeb 2 g/L for tomato fruit borer and early blight. The NKAFC bulletin also lists Mancozeb or Rovral 50 WP at 2 g/L at 10 day intervals for tomato blight, and Thiodicarb or Emamectin benzoate when fruit borer is noticed.",
            detailKn = "ಕರ್ನಾಟಕ ಕೃಷಿ ಹವಾಮಾನ ಸಲಹೆಯಲ್ಲಿ ಟೊಮ್ಯಾಟೊ ಹಣ್ಣು ಕೊರೆತ ಮತ್ತು ಆರಂಭಿಕ ಬ್ಲೈಟ್‌ಗೆ ಎಮಾಮೆಕ್ಟಿನ್ ಬೆನ್ಜೋಯೇಟ್ 0.4 ಗ್ರಾಂ/ಲೀ. ಹಾಗೂ ಮ್ಯಾಂಕೋಜೆಬ್ 2 ಗ್ರಾಂ/ಲೀ. ಶಿಫಾರಸು ಮಾಡಲಾಗಿದೆ. NKAFC ಸಲಹೆಯಲ್ಲೂ ಬ್ಲೈಟ್‌ಗೆ ಮ್ಯಾಂಕೋಜೆಬ್ ಅಥವಾ ರೋವ್ರಲ್ 50 WP 2 ಗ್ರಾಂ/ಲೀ. ಮತ್ತು ಹಣ್ಣು ಕೊರೆತ ಕಂಡಾಗ ಥಯೋಡಿಕಾರ್ಬ್ ಅಥವಾ ಎಮಾಮೆಕ್ಟಿನ್ ಬೆನ್ಜೋಯೇಟ್ ಉಲ್ಲೇಖಿಸಲಾಗಿದೆ.",
            weatherTag = "Fruit development stage",
            dosage = "Mancozeb 2 g/L; Emamectin benzoate 0.4 g/L when needed",
            precautions = listOf(
                "Do not spray without visible symptoms or field confirmation.",
                "Avoid wetting leaves during irrigation.",
                "Observe the pesticide label and waiting period before harvest."
            ),
            expert = "$SOURCE_KARNATAKA; $SOURCE_NKAFC",
            imageRes = R.drawable.tomatoes
        ),
        Tip(
            id = "coconut-basin-drainage-beetle",
            categoryId = "coconut",
            type = "Drainage and Pest",
            titleEn = "Keep coconut basins drained and mulched",
            titleKn = "ತೆಂಗಿನ ತೊಟ್ಟಿಗಳನ್ನು ನೀರು ನಿಲ್ಲದಂತೆ ಮಾಡಿ ಮಲ್ಚಿಂಗ್ ಮಾಡಿ",
            shortAdviceEn = "During nut development, maintain the basin, avoid water stagnation and use mulching to regulate soil moisture.",
            shortAdviceKn = "ಕಾಯಿ ಅಭಿವೃದ್ಧಿ ಹಂತದಲ್ಲಿ ತೊಟ್ಟಿಯನ್ನು ಸರಿಯಾಗಿ ಇಡಿ, ನೀರು ನಿಲ್ಲದಂತೆ ಮಾಡಿ ಮತ್ತು ಮಣ್ಣಿನ ತೇವಾಂಶ ಕಾಯಲು ಮಲ್ಚಿಂಗ್ ಬಳಸಿ.",
            detailEn = "The Kodagu advisory lists coconut nut development with basin maintenance, drainage and mulching. It also warns about rhinoceros beetle and bud rot, recommending neem cake and Bordeaux mixture in the crown for rot prevention.",
            detailKn = "ಕೊಡಗು ಸಲಹೆಯಲ್ಲಿ ತೆಂಗಿನ ಕಾಯಿ ಅಭಿವೃದ್ಧಿ ಹಂತಕ್ಕೆ ತೊಟ್ಟಿ ನಿರ್ವಹಣೆ, ನೀರು ಹೊರಹಾಕುವ ವ್ಯವಸ್ಥೆ ಮತ್ತು ಮಲ್ಚಿಂಗ್ ತಿಳಿಸಲಾಗಿದೆ. ರೈನೋಸೆರಸ್ ಬೀಟಲ್ ಹಾಗೂ ಬಡ್ ರಾಟ್ ಅಪಾಯಕ್ಕೆ ನೀಮ್ ಕೇಕ್ ಮತ್ತು ಕಿರೀಟ ಭಾಗದಲ್ಲಿ ಬೋರ್ಡೊ ಮಿಶ್ರಣ ಬಳಸಲು ಸೂಚಿಸಲಾಗಿದೆ.",
            weatherTag = "Humid weather disease risk",
            dosage = "Neem cake in basin; Bordeaux mixture in crown as locally advised",
            precautions = listOf(
                "Do not let water stagnate near the palm base.",
                "Keep mulch slightly away from direct stem contact.",
                "Confirm bud rot symptoms before applying crown treatment."
            ),
            expert = SOURCE_KARNATAKA,
            imageRes = R.drawable.coconut
        ),
        Tip(
            id = "arecanut-drainage-bordeaux",
            categoryId = "areca",
            type = "Disease Prevention",
            titleEn = "Improve drainage in arecanut during rain",
            titleKn = "ಮಳೆಯ ಸಮಯದಲ್ಲಿ ಅಡಿಕೆಯಲ್ಲಿ ನೀರು ಹೊರಹಾಕುವ ವ್ಯವಸ್ಥೆ ಸುಧಾರಿಸಿ",
            shortAdviceEn = "At nut development stage, keep basins drained during rainfall and avoid water stagnation to reduce fruit rot risk.",
            shortAdviceKn = "ಕಾಯಿ ಅಭಿವೃದ್ಧಿ ಹಂತದಲ್ಲಿ ಮಳೆಯ ಸಮಯದಲ್ಲಿ ತೊಟ್ಟಿಗಳಲ್ಲಿ ನೀರು ನಿಲ್ಲದಂತೆ ಮಾಡಿ, ಹಣ್ಣು ಕೊಳೆ ಅಪಾಯವನ್ನು ಕಡಿಮೆ ಮಾಡಿ.",
            detailEn = "The Kodagu advisory mentions yellow leaf disease and fruit rot in arecanut under rainfall. It recommends improving drainage, applying Bordeaux mixture and using recommended nutrients. The North Karnataka bulletin also advises mite control during flowering with Hexythiazox, Dicofol or wettable sulphur.",
            detailKn = "ಕೊಡಗು ಸಲಹೆಯಲ್ಲಿ ಮಳೆಯ ಸಮಯದಲ್ಲಿ ಅಡಿಕೆಯಲ್ಲಿ ಹಳದಿ ಎಲೆ ರೋಗ ಮತ್ತು ಹಣ್ಣು ಕೊಳೆ ಅಪಾಯ ಉಲ್ಲೇಖಿಸಲಾಗಿದೆ. ನೀರು ಹೊರಹಾಕುವ ವ್ಯವಸ್ಥೆ, ಬೋರ್ಡೊ ಮಿಶ್ರಣ ಮತ್ತು ಶಿಫಾರಸಾದ ಪೋಷಕಾಂಶಗಳನ್ನು ಬಳಸಲು ತಿಳಿಸಲಾಗಿದೆ. ಉತ್ತರ ಕರ್ನಾಟಕ ಸಲಹೆಯಲ್ಲಿ ಹೂ ಹಂತದಲ್ಲಿ ಮೈಟ್ ನಿಯಂತ್ರಣಕ್ಕೆ ಹೆಕ್ಸಿಥಿಯಾಜಾಕ್ಸ್, ಡೈಕೋಫಾಲ್ ಅಥವಾ ವೆಟಬಲ್ ಸಲ್ಫರ್ ಉಲ್ಲೇಖಿಸಲಾಗಿದೆ.",
            weatherTag = "Rainfall and humidity advisory",
            dosage = "Bordeaux mixture as advised; Hexythiazox 1.5 ml/L for mites",
            precautions = listOf(
                "Do not apply mite spray unless mites are present.",
                "Open drains before continuous rainfall.",
                "Use Bordeaux mixture only at locally recommended strength."
            ),
            expert = "$SOURCE_KARNATAKA; $SOURCE_NKAFC",
            imageRes = R.drawable.areca_cut
        ),
        Tip(
            id = "fertilizer-residue-compost",
            categoryId = "fertilizer",
            type = "Soil Health",
            titleEn = "Turn crop residues into compost",
            titleKn = "ಬೆಳೆ ಅವಶೇಷಗಳನ್ನು ಕಾಂಪೋಸ್ಟ್ ಆಗಿ ಪರಿವರ್ತಿಸಿ",
            shortAdviceEn = "After harvest, do not burn crop residues. Use them for compost or vermicompost to improve soil health.",
            shortAdviceKn = "ಕೊಯ್ಲಿನ ನಂತರ ಬೆಳೆ ಅವಶೇಷಗಳನ್ನು ಸುಡಬೇಡಿ. ಮಣ್ಣಿನ ಆರೋಗ್ಯ ಸುಧಾರಿಸಲು ಅವುಗಳನ್ನು ಕಾಂಪೋಸ್ಟ್ ಅಥವಾ ವರ್ಮಿಕಾಂಪೋಸ್ಟ್ ಮಾಡಲು ಬಳಸಿ.",
            detailEn = "The NKAFC bulletin advises farmers not to burn crop residues after harvest, and to use waste decomposer for compost and vermicompost. It also notes that crop debris mulch between plants helps maintain optimum soil moisture.",
            detailKn = "NKAFC ಸಲಹೆಯಲ್ಲಿ ಕೊಯ್ಲಿನ ನಂತರ ಬೆಳೆ ಅವಶೇಷಗಳನ್ನು ಸುಡದೆ, ವೇಸ್ಟ್ ಡಿಕಂಪೋಸರ್ ಬಳಸಿ ಕಾಂಪೋಸ್ಟ್ ಮತ್ತು ವರ್ಮಿಕಾಂಪೋಸ್ಟ್ ತಯಾರಿಸಲು ತಿಳಿಸಲಾಗಿದೆ. ಬೆಳೆಗಳ ನಡುವೆ ಅವಶೇಷ ಮಲ್ಚಿಂಗ್ ಮಣ್ಣಿನ ತೇವಾಂಶ ಕಾಯಲು ಸಹಾಯ ಮಾಡುತ್ತದೆ.",
            weatherTag = "Post-harvest soil advisory",
            dosage = "Use crop residues with compost or vermicompost process",
            precautions = listOf(
                "Keep compost heap moist but not waterlogged.",
                "Do not burn straw or stalks in the field.",
                "Use mature compost before applying near crop roots."
            ),
            expert = SOURCE_NKAFC,
            imageRes = R.drawable.fertilizers
        ),
        Tip(
            id = "irrigation-mulch-hot-weather",
            categoryId = "irrigation",
            type = "Water Management",
            titleEn = "Irrigate deserving crops and mulch between plants",
            titleKn = "ಅಗತ್ಯ ಬೆಳೆಗಳಿಗೆ ನೀರಾವರಿ ಮಾಡಿ ಮತ್ತು ಮಲ್ಚಿಂಗ್ ಬಳಸಿ",
            shortAdviceEn = "For rice, banana, sugarcane, pomegranate and other deserving crops, irrigate based on local weather and mulch between plants.",
            shortAdviceKn = "ಭತ್ತ, ಬಾಳೆ, ಕಬ್ಬು, ದಾಳಿಂಬೆ ಮತ್ತು ಇತರ ಅಗತ್ಯ ಬೆಳೆಗಳಿಗೆ ಸ್ಥಳೀಯ ಹವಾಮಾನವನ್ನು ನೋಡಿ ನೀರಾವರಿ ಮಾಡಿ ಮತ್ತು ಗಿಡಗಳ ನಡುವೆ ಮಲ್ಚಿಂಗ್ ಮಾಡಿ.",
            detailEn = "The NKAFC weather based crop advisory asks farmers to irrigate rice, banana, sugarcane, pomegranate and other deserving crops. It also recommends mulching between crop plants with crop debris to maintain optimum soil moisture.",
            detailKn = "NKAFC ಹವಾಮಾನ ಆಧಾರಿತ ಬೆಳೆ ಸಲಹೆಯಲ್ಲಿ ಭತ್ತ, ಬಾಳೆ, ಕಬ್ಬು, ದಾಳಿಂಬೆ ಮತ್ತು ಇತರ ಅಗತ್ಯ ಬೆಳೆಗಳಿಗೆ ನೀರುಣಿಸಲು ತಿಳಿಸಲಾಗಿದೆ. ಗಿಡಗಳ ನಡುವೆ ಬೆಳೆ ಅವಶೇಷ ಮಲ್ಚಿಂಗ್ ಮಾಡುವುದರಿಂದ ಮಣ್ಣಿನ ತೇವಾಂಶ ಕಾಯುತ್ತದೆ.",
            weatherTag = "Hot weather moisture advisory",
            dosage = "Irrigate by crop need; use crop debris as mulch",
            precautions = listOf(
                "Check soil moisture before irrigation.",
                "Avoid water stagnation in heavy soils.",
                "Do not irrigate during strong wind or thunderstorm activity."
            ),
            expert = SOURCE_NKAFC,
            imageRes = R.drawable.irrigation
        ),
        Tip(
            id = "pest-safe-spray-wind",
            categoryId = "pest",
            type = "Spray Safety",
            titleEn = "Avoid spraying during high wind",
            titleKn = "ಹೆಚ್ಚು ಗಾಳಿ ಇದ್ದಾಗ ಸಿಂಪಡಣೆ ತಪ್ಪಿಸಿ",
            shortAdviceEn = "If gusty wind or thunderstorm is forecast, postpone pesticide or herbicide spraying and protect harvested produce.",
            shortAdviceKn = "ಜೋರಾದ ಗಾಳಿ ಅಥವಾ ಗುಡುಗು ಮಳೆ ಮುನ್ಸೂಚನೆ ಇದ್ದರೆ ಕೀಟನಾಶಕ ಅಥವಾ ಕಳೆನಾಶಕ ಸಿಂಪಡಣೆಯನ್ನು ಮುಂದೂಡಿ ಮತ್ತು ಕೊಯ್ಲಾದ ಉತ್ಪನ್ನವನ್ನು ರಕ್ಷಿಸಿ.",
            detailEn = "The agromet PDFs advise farmers to avoid spraying during high wind speeds. In possible thunderstorms, provide support to crops, drain excess water, cover harvested produce and keep livestock sheltered away from water bodies and metal objects.",
            detailKn = "ಕೃಷಿ ಹವಾಮಾನ PDFಗಳಲ್ಲಿ ಹೆಚ್ಚು ಗಾಳಿ ಇದ್ದಾಗ ಸಿಂಪಡಣೆ ತಪ್ಪಿಸಲು ಸಲಹೆ ನೀಡಲಾಗಿದೆ. ಗುಡುಗು ಮಳೆ ಸಾಧ್ಯತೆಯಲ್ಲಿ ಬೆಳೆಗಳಿಗೆ ಆಧಾರ ನೀಡಿ, ಹೆಚ್ಚುವರಿ ನೀರು ಹೊರಹಾಕಿ, ಕೊಯ್ಲಾದ ಉತ್ಪನ್ನವನ್ನು ಮುಚ್ಚಿ ಮತ್ತು ಜಾನುವಾರುಗಳನ್ನು ನೀರಿನ ತಟ ಹಾಗೂ ಲೋಹದ ವಸ್ತುಗಳಿಂದ ದೂರ ಸುರಕ್ಷಿತ ಆಶ್ರಯದಲ್ಲಿ ಇರಿಸಿ.",
            weatherTag = "Thunderstorm and gusty wind advisory",
            dosage = "Spray only in calm morning or evening conditions",
            precautions = listOf(
                "Do not stand under trees during lightning.",
                "Unplug electrical equipment before storm activity.",
                "Keep pesticide containers away from children and animals."
            ),
            expert = "$SOURCE_KARNATAKA; $SOURCE_NKAFC",
            imageRes = R.drawable.pest_control
        ),
        Tip(
            id = "maize-border-crop-chilli",
            categoryId = "corn",
            type = "Border Crop",
            titleEn = "Use maize or sorghum as a border crop",
            titleKn = "ಮೆಕ್ಕೆಜೋಳ ಅಥವಾ ಜೋಳವನ್ನು ಗಡಿ ಬೆಳೆ ಆಗಿ ಬಳಸಿ",
            shortAdviceEn = "For chilli fields, grow maize or sorghum as a border crop to reduce the spread of sucking insects like thrips and mites.",
            shortAdviceKn = "ಮೆಣಸಿನಕಾಯಿ ಹೊಲಗಳಲ್ಲಿ ಥ್ರಿಪ್ಸ್ ಮತ್ತು ಮೈಟ್‌ಗಳಂತಹ ಚುಚ್ಚು-ಹೀರುವ ಕೀಟಗಳ ಹರಡುವಿಕೆ ಕಡಿಮೆ ಮಾಡಲು ಮೆಕ್ಕೆಜೋಳ ಅಥವಾ ಜೋಳವನ್ನು ಗಡಿ ಬೆಳೆ ಆಗಿ ಬೆಳೆಸಿ.",
            detailEn = "The NKAFC horticulture advisory recommends maize or sorghum as a border crop for chilli to prevent the spread of sucking insects. This is a cultural control measure that can reduce pest pressure before chemical spraying is needed.",
            detailKn = "NKAFC ತೋಟಗಾರಿಕಾ ಸಲಹೆಯಲ್ಲಿ ಮೆಣಸಿನಕಾಯಿಯಲ್ಲಿ ಚುಚ್ಚು-ಹೀರುವ ಕೀಟಗಳ ಹರಡುವಿಕೆ ಕಡಿಮೆ ಮಾಡಲು ಮೆಕ್ಕೆಜೋಳ ಅಥವಾ ಜೋಳವನ್ನು ಗಡಿ ಬೆಳೆ ಆಗಿ ಬೆಳೆಸಲು ಹೇಳಲಾಗಿದೆ. ಇದು ರಾಸಾಯನಿಕ ಸಿಂಪಡಣೆಗೆ ಮುನ್ನ ಕೀಟ ಒತ್ತಡ ಕಡಿಮೆ ಮಾಡುವ ಸಾಂಸ್ಕೃತಿಕ ಕ್ರಮ.",
            weatherTag = "Vegetative stage pest barrier",
            dosage = "Plant border rows around chilli plots",
            precautions = listOf(
                "Use border crop before pest pressure becomes severe.",
                "Continue scouting for thrips, mites and whitefly.",
                "Do not rely on border crop alone if pest load is high."
            ),
            expert = SOURCE_NKAFC,
            imageRes = R.drawable.corn
        )
    )

    val stories = listOf(
        SuccessStory(
            farmerName = "Krishi Vignana",
            district = "Bengaluru",
            crop = "Rainwater",
            result = "Water saved",
            storyEn = "The Kannada quarterly shows how farm ponds and rainwater harvesting can support crops during dry periods.",
            storyKn = "ಕನ್ನಡ ತ್ರೈಮಾಸಿಕದಲ್ಲಿ ಕೃಷಿ ಹೊಂಡ ಮತ್ತು ಮಳೆ ನೀರು ಕೊಯ್ಲು ಒಣ ಅವಧಿಯಲ್ಲಿ ಬೆಳೆಗಳಿಗೆ ನೆರವಾಗುವುದನ್ನು ತೋರಿಸಲಾಗಿದೆ.",
            imageRes = R.drawable.kv_rainwater
        ),
        SuccessStory(
            farmerName = "Krishi Vignana",
            district = "Bengaluru",
            crop = "Vermicompost",
            result = "Soil health",
            storyEn = "Integrated farming with vermicompost converts farm waste into a useful nutrient source.",
            storyKn = "ವರ್ಮಿಕಾಂಪೋಸ್ಟ್ ಹೊಂದಿದ ಸಮಗ್ರ ಕೃಷಿ ಕೃಷಿ ತ್ಯಾಜ್ಯವನ್ನು ಉಪಯುಕ್ತ ಪೋಷಕಾಂಶವಾಗಿ ಪರಿವರ್ತಿಸುತ್ತದೆ.",
            imageRes = R.drawable.kv_vermicompost
        ),
        SuccessStory(
            farmerName = "Mysore/Mandya advisory",
            district = "Mysuru and Mandya",
            crop = "Tomato",
            result = "Blight watch",
            storyEn = "The district advisory asks tomato growers to keep irrigation uniform and monitor fruit borer and early blight during fruit development.",
            storyKn = "ಜಿಲ್ಲಾ ಸಲಹೆಯಲ್ಲಿ ಟೊಮ್ಯಾಟೊ ಬೆಳೆಗಾರರಿಗೆ ಹಣ್ಣು ಅಭಿವೃದ್ಧಿ ಹಂತದಲ್ಲಿ ಸಮ ನೀರಾವರಿ ಮಾಡುವುದು ಮತ್ತು ಹಣ್ಣು ಕೊರೆತ ಹಾಗೂ ಆರಂಭಿಕ ಬ್ಲೈಟ್ ಗಮನಿಸುವುದು ತಿಳಿಸಲಾಗಿದೆ.",
            imageRes = R.drawable.tomatoes
        )
    )

    fun categoryName(id: String, language: String): String {
        val category = categories.firstOrNull { it.id == id } ?: return id
        return if (language == "kn") category.kn else category.en
    }

    fun expertAnalysis(crop: String): ExpertAnalysis {
        return when (crop.trim().lowercase()) {
            "paddy" -> ExpertAnalysis(
                disease = "Stem borer, leaf folder or blast risk",
                diseaseKn = "ಕಾಂಡ ಕೊರೆತ, ಎಲೆ ಮಡಚು ಹುಳು ಅಥವಾ ಬ್ಲಾಸ್ಟ್ ಅಪಾಯ",
                confidence = "Source-backed",
                severity = "Check field symptoms before treatment",
                treatmentSteps = listOf(
                    "Maintain 2-3 cm water in early paddy and keep drainage ready during light rains.",
                    "Install pheromone traps at 5 per acre for stem borer monitoring.",
                    "Use Chlorantraniliprole 0.4 ml/L or Tricyclazole 0.6 g/L only when symptoms match local officer advice."
                ),
                prevention = listOf(
                    "Avoid moisture stress during transplanting and early vegetative stage.",
                    "Scout the field after rain and cloudy weather.",
                    "Do not apply plant protection chemicals during strong wind."
                )
            )
            "tomato" -> ExpertAnalysis(
                disease = "Fruit borer or early blight risk",
                diseaseKn = "ಹಣ್ಣು ಕೊರೆತ ಅಥವಾ ಆರಂಭಿಕ ಬ್ಲೈಟ್ ಅಪಾಯ",
                confidence = "Source-backed",
                severity = "Moderate during fruit development",
                treatmentSteps = listOf(
                    "Keep irrigation uniform to reduce fruit cracking.",
                    "For early blight, use Mancozeb 2 g/L after symptoms are seen.",
                    "For fruit borer, use Emamectin benzoate 0.4 g/L after confirming infestation."
                ),
                prevention = listOf(
                    "Avoid wetting foliage while irrigating.",
                    "Remove badly affected fruits and leaves from the plot.",
                    "Follow pesticide waiting period before harvest."
                )
            )
            "coconut" -> ExpertAnalysis(
                disease = "Rhinoceros beetle or bud rot risk",
                diseaseKn = "ರೈನೋಸೆರಸ್ ಬೀಟಲ್ ಅಥವಾ ಬಡ್ ರಾಟ್ ಅಪಾಯ",
                confidence = "Source-backed",
                severity = "Watch closely in humid weather",
                treatmentSteps = listOf(
                    "Maintain coconut basins and improve drainage.",
                    "Use mulching to regulate soil moisture.",
                    "Apply neem cake and Bordeaux mixture in the crown only as locally advised."
                ),
                prevention = listOf(
                    "Remove water stagnation around the palm.",
                    "Inspect the crown and young leaves regularly.",
                    "Keep mulch away from direct stem contact."
                )
            )
            "areca", "arecanut", "areca nut" -> ExpertAnalysis(
                disease = "Fruit rot, yellow leaf or mite risk",
                diseaseKn = "ಹಣ್ಣು ಕೊಳೆ, ಹಳದಿ ಎಲೆ ರೋಗ ಅಥವಾ ಮೈಟ್ ಅಪಾಯ",
                confidence = "Source-backed",
                severity = "Rainfall and flowering stage sensitive",
                treatmentSteps = listOf(
                    "Improve basin drainage during rainfall.",
                    "Apply Bordeaux mixture for fruit rot prevention after local confirmation.",
                    "For mites, use Hexythiazox 1.5 ml/L or other locally recommended option only if mites are present."
                ),
                prevention = listOf(
                    "Avoid water stagnation in the basin.",
                    "Apply recommended nutrients on schedule.",
                    "Scout palms during flowering and nut development."
                )
            )
            "maize", "corn" -> ExpertAnalysis(
                disease = "Sucking insect barrier support",
                diseaseKn = "ಚುಚ್ಚು-ಹೀರುವ ಕೀಟ ತಡೆ ಬೆಂಬಲ",
                confidence = "Source-backed",
                severity = "Preventive cultural measure",
                treatmentSteps = listOf(
                    "Use maize or sorghum border rows around chilli fields.",
                    "Continue field scouting for thrips, mites and whitefly.",
                    "Use chemical treatment only if pest pressure crosses local threshold."
                ),
                prevention = listOf(
                    "Plant border crop before pest pressure builds.",
                    "Keep crop spacing and weed control clean.",
                    "Avoid unnecessary insecticide sprays."
                )
            )
            "chilli", "chili" -> ExpertAnalysis(
                disease = "Fruit rot, leaf curl, thrips or whitefly risk",
                diseaseKn = "ಹಣ್ಣು ಕೊಳೆ, ಎಲೆ ಮಡಚು, ಥ್ರಿಪ್ಸ್ ಅಥವಾ ವೈಟ್‌ಫ್ಲೈ ಅಪಾಯ",
                confidence = "Source-backed",
                severity = "Scout during vegetative stage",
                treatmentSteps = listOf(
                    "For fruit rot, use Difenoconazole 1 ml/L or Carbendazim 1 g/L after symptoms are confirmed.",
                    "For thrips or whitefly at seedling stage, use Imidacloprid 17.8 SL at 0.5 ml/L as advised.",
                    "Grow maize or sorghum as border crop to reduce sucking insect spread."
                ),
                prevention = listOf(
                    "Monitor the underside of leaves for curling and sucking pests.",
                    "Avoid repeated sprays of the same chemical.",
                    "Use neem-based options and border crops early in the season."
                )
            )
            else -> ExpertAnalysis(
                disease = "Weather based crop stress",
                diseaseKn = "ಹವಾಮಾನ ಆಧಾರಿತ ಬೆಳೆ ಒತ್ತಡ",
                confidence = "Source-backed",
                severity = "Use local crop symptoms",
                treatmentSteps = listOf(
                    "Check soil moisture and drainage before applying irrigation or fertilizer.",
                    "Postpone spraying during strong wind or thunderstorm conditions.",
                    "Use crop residue mulch to conserve soil moisture where suitable."
                ),
                prevention = listOf(
                    "Keep harvested produce covered before rain.",
                    "Keep livestock and equipment away from water and metal during lightning.",
                    "Confirm pest or disease symptoms with a local agriculture officer."
                )
            )
        }
    }
}
