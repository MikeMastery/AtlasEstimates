package com.example.atlasestimates.ui.dashboard;

import static android.content.Intent.getIntent;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.atlasestimates.AppDatabase;
import com.example.atlasestimates.CotizacionViewModel;
import com.example.atlasestimates.R;
import com.example.atlasestimates.databinding.FragmentDashboardBinding;

import java.util.ArrayList;
import java.util.List;

public class DashboardFragment extends Fragment {

    private FragmentDashboardBinding binding;
    private RecyclerView recyclerView;
    private ItemAdapter adapter;
    private List<Item> itemsList;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        // Inicializa el binding
        binding = FragmentDashboardBinding.inflate(inflater, container, false);
        View root = binding.getRoot();



        CotizacionViewModel viewModel = new ViewModelProvider(requireActivity()).get(CotizacionViewModel.class);






        // Configurar RecyclerView
        recyclerView = root.findViewById(R.id.recyclerViewProductos);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));



        // Inicializar la lista
        itemsList = new ArrayList<>();

        // Definir el botón de servicios
        binding.servicio.setOnClickListener(v -> {
            cargarServiciosEstaticos();
            adapter.notifyDataSetChanged();
        });

        // Definir el botón de productos
        binding.addProduct.setOnClickListener(v -> {
            cargarProductosEstaticos();
            adapter.notifyDataSetChanged();
        });

        // Configurar el adaptador
        adapter = new ItemAdapter(itemsList);
        recyclerView.setAdapter(adapter);

        // Cargar productos por defecto
        cargarProductosEstaticos();

        return root;
    }



    private void cargarServiciosEstaticos() {
        itemsList.clear();

        // Añadir servicios estáticos con descripciones detalladas
        itemsList.add(new Item("Ingeniería",
                "Instalación, mantenimiento y optimización de sistemas eléctricos, hidráulicos y estructuras mecánicas en proyectos industriales, comerciales y residenciales. Los ingenieros especializados en estos campos aseguran la calidad, la viabilidad técnica y la eficiencia de las instalaciones dentro de un proyecto de gran escala, garantizando el cumplimiento con las normativas locales y la sostenibilidad del proyecto. Este servicio incluye desde el diseño hasta la implementación y puesta en marcha de sistemas técnicos avanzados.",
                R.drawable.ingenieria));

        itemsList.add(new Item("Arquitectura",
                "Consultoría técnica en diseño y planificación arquitectónica para edificios residenciales, comerciales, urbanos y paisajismo. Los arquitectos se encargan de crear soluciones innovadoras y funcionales, optimizando los espacios en función de las necesidades del cliente, respetando las normativas locales de construcción, la estética y la sostenibilidad en el diseño de espacios habitables y públicos. Además, asesoran en la selección de materiales, eficiencia energética y el diseño de estructuras que maximicen la seguridad y el confort.",
                R.drawable.arquitectura));

        itemsList.add(new Item("Topografía",
                "Servicios avanzados de medición, análisis y levantamiento de terrenos para proyectos de construcción, utilizando tecnología de punta como GPS, láser y drones. La topografía es esencial para garantizar que las construcciones sean seguras, estables y precisas, cumpliendo con las regulaciones locales. Este servicio incluye la elaboración de planos topográficos, análisis del terreno y recomendaciones sobre cómo desarrollar proyectos en terrenos con desafíos topográficos o ambientales.",
                R.drawable.topografia));

        itemsList.add(new Item("Maquinaria Pesada",
                "Asesoría legal y operativa en la adquisición, mantenimiento y uso eficiente de maquinaria pesada en sectores como la construcción, minería, obra pública y otros. Este servicio cubre desde el análisis de la compra y arrendamiento de maquinaria hasta la optimización de su uso en proyectos a gran escala, asegurando el cumplimiento de normativas legales, seguridad operativa, y la mejora de la productividad mediante el mantenimiento adecuado y la capacitación del personal.",
                R.drawable.maquinaria));

        itemsList.add(new Item("Abastecimiento de agua",
                "Consultoría en el diseño, gestión y optimización de sistemas de abastecimiento de agua potable para áreas urbanas, rurales e industriales. Este servicio incluye la planificación de redes de distribución de agua, tratamiento de aguas residuales, soluciones sostenibles para la captación de agua y el diseño de infraestructuras hidráulicas que aseguren el acceso eficiente y económico al agua, mejorando la calidad de vida y minimizando el impacto ambiental.",
                R.drawable.abasagua));

        itemsList.add(new Item("Estructuras Metálicas",
                "Asesoría en el diseño, fabricación, montaje y mantenimiento de estructuras metálicas, que incluyen desde edificios de acero hasta puentes, naves industriales y otras grandes construcciones. Los expertos en estructuras metálicas asesoran sobre la resistencia de los materiales, el cumplimiento de normas de seguridad, la eficiencia en los costes y la durabilidad de las estructuras, adaptándose a proyectos tanto comerciales como industriales.",
                R.drawable.estructuras));
    }

    private void cargarProductosEstaticos() {
        itemsList.clear();

        // Añadir productos estáticos con descripciones detalladas
        itemsList.add(new Item("Cercos Prefabricados",
                "Muros de concreto prefabricados que ofrecen soluciones estéticas y funcionales para la delimitación de propiedades. Además de proporcionar seguridad y privacidad, estos cercos permiten personalizar los diseños con acabados diversos, adaptándose a las necesidades de cada cliente. Son ideales para áreas residenciales, comerciales e industriales, ofreciendo una instalación rápida y sin complicaciones, con una alta durabilidad frente a condiciones climáticas adversas.",
                R.drawable.cercos));

        itemsList.add(new Item("Block Concreto",
                "Bloques modulares de concreto, utilizados en la construcción de muros de albañilería confinada y armada. Son esenciales para la edificación de cimientos, paredes estructurales y divisorias internas en obras de mediana y gran envergadura. Estos bloques ofrecen resistencia a la compresión, facilidad de manejo y colocación, y son una opción económica para la construcción. Además, contribuyen al aislamiento térmico y acústico de los edificios, mejorando el confort de los espacios.",
                R.drawable.block));

        itemsList.add(new Item("Cerco Tipo cabeza de Caballo",
                "Elementos modulares de concreto, diseñados para crear muros estructurales resistentes que se emplean en la construcción de edificios, caminos y otras infraestructuras. Los muretes de concreto ofrecen durabilidad, estabilidad y una excelente capacidad para resistir cargas. Son ideales para proyectos en los que se requiere una base sólida y confiable, con la ventaja de ser fáciles de transportar e instalar.",
                R.drawable.murete));

        itemsList.add(new Item("Poste de Concreto",
                "Postes de concreto prefabricado con refuerzos de acero, diseñados para resistir altas cargas de flexión y tensión. Son comúnmente utilizados en la instalación de líneas eléctricas, sistemas de telecomunicaciones, alumbrado público y como soporte para otras estructuras verticales. Gracias a su robustez y durabilidad, los postes de concreto garantizan una vida útil prolongada y una alta resistencia a condiciones climáticas extremas, como vientos fuertes y lluvias.",
                R.drawable.poste));
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    // Adaptador para RecyclerView
    public static class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ItemViewHolder> {

        private List<Item> itemsList;

        public ItemAdapter(List<Item> itemsList) {
            this.itemsList = itemsList;
        }

        @NonNull
        @Override
        public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.tarjetas, parent, false);
            return new ItemViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
            Item item = itemsList.get(position);
            holder.imageView.setImageResource(item.getImageResource());
            holder.nameView.setText(item.getName());
            holder.descriptionView.setText(item.getDescription());

            // Manejar el clic en la tarjeta
            holder.itemView.setOnClickListener(v -> {
                // Enviar el ítem a la actividad de detalles
                Intent intent = new Intent(v.getContext(), activity_product_detail.class);
                intent.putExtra("selectedItem", item); // Pasar el item seleccionado

                v.getContext().startActivity(intent);
            });
        }

        @Override
        public int getItemCount() {
            return itemsList.size();
        }

        // ViewHolder para el RecyclerView
        public static class ItemViewHolder extends RecyclerView.ViewHolder {
            ImageView imageView;
            TextView nameView;
            TextView descriptionView;

            public ItemViewHolder(View itemView) {
                super(itemView);
                imageView = itemView.findViewById(R.id.ivProductImage);
                nameView = itemView.findViewById(R.id.tvProductName);
                descriptionView = itemView.findViewById(R.id.tvProductDescription);

            }
        }
    }

    // Clase que representa los items (productos o servicios)
    public static class Item implements java.io.Serializable {
        private String name;
        private String description;
        private int imageResource;

        public Item(String name, String description, int imageResource) {
            this.name = name;
            this.description = description;
            this.imageResource = imageResource;
        }

        public String getName() {
            return name;
        }

        public String getDescription() {
            return description;
        }

        public int getImageResource() {
            return imageResource;
        }
    }
}
