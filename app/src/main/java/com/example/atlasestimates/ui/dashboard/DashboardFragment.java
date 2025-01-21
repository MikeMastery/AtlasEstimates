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
import androidx.lifecycle.LifecycleOwner;
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
    private CotizacionViewModel viewModel;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        // Inicializa el binding
        binding = FragmentDashboardBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        // Inicializar ViewModel
        TextView tiposItemsTextView = root.findViewById(R.id.tipositems);

        viewModel = new ViewModelProvider(requireActivity()).get(CotizacionViewModel.class);

        // Configurar RecyclerView
        recyclerView = root.findViewById(R.id.recyclerViewProductos);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));

        // Inicializar la lista
        itemsList = new ArrayList<>();

        // Definir el botón de servicios
        binding.servicio.setOnClickListener(v -> {
            cargarServiciosEstaticos();
            adapter.notifyDataSetChanged();
            tiposItemsTextView.setText("Servicios Disponibles");
        });

        // Definir el botón de productos
        binding.addProduct.setOnClickListener(v -> {
            cargarProductosEstaticos();
            adapter.notifyDataSetChanged();
            tiposItemsTextView.setText("Productos Disponibles");
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

        itemsList.add(new Item("Ingeniería",
                "Proveemos servicios integrales de ingeniería especializados en la instalación, mantenimiento y optimización de sistemas eléctricos, hidráulicos y estructuras mecánicas. Nuestros proyectos abarcan desde pequeñas obras residenciales hasta complejas instalaciones industriales. Contamos con personal altamente calificado, herramientas de última tecnología y un enfoque en la sostenibilidad para garantizar soluciones eficientes, seguras y duraderas. Además, ofrecemos consultoría técnica personalizada para asegurar que cada proyecto cumpla con las normativas vigentes y las expectativas del cliente.",
                R.drawable.ingenieria));

        itemsList.add(new Item("Arquitectura",
                "Ofrecemos servicios de diseño arquitectónico y planificación urbana con un enfoque innovador y sostenible. Nuestro equipo de expertos desarrolla proyectos para edificios residenciales, comerciales y de uso mixto, combinando funcionalidad, estética y eficiencia energética. También proporcionamos consultoría técnica para optimizar espacios interiores y exteriores, asegurando que se adapten a las necesidades específicas de nuestros clientes. Desde los bocetos iniciales hasta la ejecución final, trabajamos de la mano con los usuarios para transformar sus ideas en realidad.",
                R.drawable.arquitectura));

        itemsList.add(new Item("Topografía",
                "Proveemos servicios de topografía avanzada, incluyendo levantamientos geodésicos, mediciones de alta precisión y análisis de terrenos. Utilizamos equipos modernos como drones, estaciones totales y GPS para garantizar datos precisos en proyectos de construcción, infraestructura y planificación urbana. Nuestra experiencia incluye trabajos en terrenos complejos, delimitación de propiedades y estudios para obras de gran envergadura. Además, ofrecemos asesoramiento técnico para integrar los datos topográficos en el diseño y ejecución de proyectos.",
                R.drawable.topografia));

        itemsList.add(new Item("Maquinaria Pesada",
                "Brindamos asesoría integral para la adquisición, mantenimiento y operación de maquinaria pesada en proyectos de construcción e industriales. Nuestros servicios incluyen análisis técnico de equipos, formación en manejo seguro y eficiente, y soporte técnico en tiempo real. También gestionamos la optimización del rendimiento de la maquinaria para maximizar su productividad y minimizar costos operativos. Nos especializamos en excavadoras, cargadores, grúas, compactadoras y otros equipos esenciales para obras de gran escala.",
                R.drawable.maquinaria));

        itemsList.add(new Item("Abastecimiento de agua",
                "Especializados en el diseño, implementación y mantenimiento de sistemas de abastecimiento de agua potable para comunidades urbanas y rurales. Nuestro enfoque incluye soluciones innovadoras como sistemas de captación de agua de lluvia, plantas de tratamiento y redes de distribución eficientes. Trabajamos en proyectos que garantizan un suministro constante y seguro, cumpliendo con los más altos estándares de calidad y sostenibilidad ambiental. Además, proporcionamos estudios técnicos para optimizar el uso y conservación del recurso hídrico.",
                R.drawable.abasagua));

        itemsList.add(new Item("Estructuras Metálicas",
                "Ofrecemos servicios especializados en diseño, fabricación, montaje y mantenimiento de estructuras metálicas para proyectos industriales, comerciales y residenciales. Nuestro equipo utiliza tecnología de punta y materiales de alta calidad para garantizar soluciones robustas y estéticas. Diseñamos estructuras adaptadas a las necesidades específicas del cliente, desde naves industriales y puentes hasta fachadas arquitectónicas. Además, cumplimos con normativas de seguridad y realizamos inspecciones periódicas para garantizar la durabilidad de nuestras construcciones.",
                R.drawable.estructuras));
    }


    private void cargarProductosEstaticos() {
        itemsList.clear();

        itemsList.add(new Item("Cercos prefabricados",
                "Muros de concreto prefabricados diseñados para proporcionar una solución estética, duradera y funcional en la delimitación de propiedades residenciales, comerciales e industriales. Estos cercos están fabricados con concreto de alta resistencia y acabados suaves, ofreciendo un balance perfecto entre robustez y apariencia visual. Su instalación es rápida y eficiente gracias a su diseño modular, lo que reduce tiempos y costos de construcción. Son ideales para mejorar la seguridad y privacidad, al mismo tiempo que se integran armoniosamente con el entorno.",
                R.drawable.cercos));

        itemsList.add(new Item("Block de concreto",
                "Bloques modulares de concreto fabricados con precisión para garantizar calidad y resistencia en cada pieza. Ideales para la construcción de muros de albañilería confinada y armada, estos bloques destacan por su resistencia estructural, facilidad de manejo y durabilidad frente a condiciones climáticas extremas. Ofrecen aislamiento térmico y acústico, lo que contribuye al ahorro energético en edificios. Disponibles en diferentes tamaños y estilos, son una opción versátil tanto para proyectos residenciales como comerciales.",
                R.drawable.block));

        itemsList.add(new Item("Cerco cabeza caballo",
                "Diseñado con un toque único y decorativo, el cerco cabeza caballo combina funcionalidad con estética. Fabricado con concreto reforzado de alta calidad, este elemento modular ofrece una solución resistente para muros estructurales en propiedades residenciales, rurales o comerciales. Su distintivo diseño con detalles decorativos en forma de cabeza de caballo no solo mejora la apariencia del entorno, sino que también proporciona una barrera sólida y duradera contra el acceso no autorizado. Su instalación modular garantiza rapidez y precisión, ahorrando tiempo y costos.",
                R.drawable.murete));

        itemsList.add(new Item("Poste de concreto",
                "Postes de concreto prefabricado diseñados para cumplir con los más altos estándares de ingeniería y durabilidad. Reforzados con acero estructural, son ideales para una amplia gama de aplicaciones, desde cercos perimetrales hasta instalaciones eléctricas y telecomunicaciones. Estos postes son resistentes a condiciones climáticas adversas, como lluvia, viento y exposición prolongada al sol, garantizando una vida útil prolongada. Además, su diseño optimizado facilita el transporte y la instalación, lo que los convierte en una opción confiable para proyectos urbanos y rurales.",
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
                Intent intent = new Intent(v.getContext(), activity_product_detail.class);
                intent.putExtra("selectedItem", item);
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