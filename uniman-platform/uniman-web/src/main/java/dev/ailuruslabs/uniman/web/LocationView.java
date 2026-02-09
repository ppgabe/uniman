package dev.ailuruslabs.uniman.web;

import com.vaadin.flow.component.HasValue;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import dev.ailuruslabs.uniman.domain.common.locations.Barangay;
import dev.ailuruslabs.uniman.domain.common.locations.Municipality;
import dev.ailuruslabs.uniman.domain.common.locations.Province;
import dev.ailuruslabs.uniman.domain.common.locations.Region;
import jakarta.inject.Inject;

import java.util.Objects;

/**
 * PSGC Locations Drop-down Proof-of-Concept w/ Vaadin
 * This is my first PoC, showcasing interactive dropdown for
 * regions, provinces/HUCs, municipalities, and barangays!
 *
 * This code is very much PoC-like. It's very rough, there's
 * tons of code duplication everywhere, and there's
 * no fancy layout at all. But it has a real connection to
 * the database and represents the full lifecycle, from the
 * view all the way down to the domain layer.
 *
 * @author Gabriel Virrey
 * @since 0.1.0
 */
@Route("")
public class LocationView extends VerticalLayout {

    private final LocationService locationService;

    @Inject
    public LocationView(LocationService locationService) {
        this.locationService = locationService;
        initialize();
    }

    private void initialize() {
        TextField psgc = new TextField("10-digit PSGC");
        psgc.setReadOnly(true);

        var regionsList = locationService.getRegions();

        var regionComboBox = new ComboBox<Region>("Region");
        regionComboBox.setItemLabelGenerator(Region::name);
        regionComboBox.setItems(regionsList);

        var provinceComboBox = new ComboBox<Province>("Province/HUC");
        provinceComboBox.setItemLabelGenerator(Province::getName);
        provinceComboBox.setEnabled(false);

        var municipalityComboBox = new ComboBox<Municipality>("Municipality");
        municipalityComboBox.setItemLabelGenerator(Municipality::getName);
        municipalityComboBox.setEnabled(false);

        var barangayComboBox = new ComboBox<Barangay>("Barangay");
        barangayComboBox.setItemLabelGenerator(Barangay::getName);
        barangayComboBox.setEnabled(false);
        municipalityComboBox.addValueChangeListener(event -> {
            if (event.getValue() == null) {
                barangayComboBox.setValue(null);
                return;
            }

            var barangaysList =
                    locationService.getBarangaysByMunicipalityId(event.getValue().getId());
            barangayComboBox.setItems(barangaysList);
            barangayComboBox.setEnabled(true);

            psgc.setValue(psgc.getValue().substring(0, 5) + event.getValue().getCode() + "000");
        });

        setComboBoxesRequired(regionComboBox, provinceComboBox, municipalityComboBox, barangayComboBox);

        provinceComboBox.addValueChangeListener(event -> {
            if (event.getValue() == null) {
                municipalityComboBox.setValue(null);
                return;
            }

            var municipalitiesList = locationService.getMunicipalitesByProvinceId(event.getValue().getId());

            municipalityComboBox.setItems(municipalitiesList);
            municipalityComboBox.setEnabled(true);

            psgc.setValue(psgc.getValue().substring(0, 2) + event.getValue().getCode() + "0".repeat(5));
        });

        regionComboBox.addValueChangeListener(event -> {
            var provincesList = locationService.getProvincesByRegionCode(event.getValue().code());

            provinceComboBox.setItems(provincesList);
            provinceComboBox.setEnabled(true);

            provinceComboBox.setValue(null);
            psgc.setValue(event.getValue().code() + "0".repeat(8));
        });


        barangayComboBox.addValueChangeListener(event -> {
            if (event.getValue() == null) {
                return;
            }

            psgc.setValue(psgc.getValue().substring(0, 7) + event.getValue().getCode());
        });

        add(regionComboBox, provinceComboBox, municipalityComboBox, barangayComboBox, psgc);
    }

    private void setComboBoxesRequired(ComboBox<?>... comboBoxes) {
        for (var comboBox : comboBoxes) {
            if (Objects.nonNull(comboBox)) {
                comboBox.setRequiredIndicatorVisible(true);
            }
        }
    }
}
