package org.partnerprod.partnerprod.servicio;

import org.partnerprod.partnerprod.modelo.Escena;
import org.partnerprod.partnerprod.modelo.Item;
import org.partnerprod.partnerprod.modelo.Plan;
import org.partnerprod.partnerprod.modelo.PlanItem;
import org.partnerprod.partnerprod.repositorio.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class PlanServicio {
    @Autowired
    private PlanRepositorio planRepositorio;

    @Autowired
    private EscenaRepositorio escenaRepositorio;

    @Autowired
    private ItemRepositorio itemRepositorio;

    @Autowired
    private PlanItemRepositorio planItemRepositorio;

    @Autowired
    private BodegaVirtualRepositorio bodegaVirtualRepositorio;

    public Plan guardarPlan(Plan plan) {
        return planRepositorio.save(plan);
    }

    public Plan obtenerPlanPorId(Long id) {
        Optional<Plan> planOptional = planRepositorio.findById(id);
        if (planOptional.isPresent()) {
            Plan plan = planOptional.get();
            plan.setPlanItems(planItemRepositorio.findByPlanId(id));
            return plan;
        }
        return null;
    }

    public List<Plan> obtenerTodosLosPlanes() {
        return planRepositorio.findAll();
    }

    @Transactional
    public void eliminarPlan(Long id) {
        Plan plan = planRepositorio.findById(id)
                .orElseThrow(() -> new RuntimeException("Plan no encontrado con id: " + id));

        // La eliminación en cascada se encargará de eliminar las PlanEscenaEtiqueta asociadas
        planRepositorio.delete(plan);
    }

    public List<Plan> obtenerPlanesPorProyecto(Long proyectoId) {
        return planRepositorio.findByProyectoId(proyectoId);
    }

    @Transactional
    public void agregarItemAPlan(Long planId, Long itemId, int cantidad) {
        Plan plan = planRepositorio.findById(planId)
                .orElseThrow(() -> new RuntimeException("Plan no encontrado"));
        Item item = itemRepositorio.findById(itemId)
                .orElseThrow(() -> new RuntimeException("Item no encontrado"));

        if (item.getCantidad() < cantidad) {
            throw new RuntimeException("No hay suficientes unidades del item en el inventario");
        }

        PlanItem planItem = new PlanItem(plan, item, cantidad);
        plan.getPlanItems().add(planItem);
        item.setCantidad(item.getCantidad() - cantidad);

        planItemRepositorio.save(planItem);
        itemRepositorio.save(item);
    }

    @Transactional
    public void devolverItemDePlan(Long planId, Long itemId, int cantidad) {
        Plan plan = planRepositorio.findById(planId)
                .orElseThrow(() -> new RuntimeException("Plan no encontrado"));
        Item item = itemRepositorio.findById(itemId)
                .orElseThrow(() -> new RuntimeException("Item no encontrado"));

        PlanItem planItem = plan.getPlanItems().stream()
                .filter(pi -> pi.getItem().getId().equals(itemId))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Item no encontrado en el plan"));

        if (planItem.getCantidad() < cantidad) {
            throw new RuntimeException("No se pueden devolver más items de los que se tomaron");
        }

        planItem.setCantidad(planItem.getCantidad() - cantidad);
        item.setCantidad(item.getCantidad() + cantidad);

        if (planItem.getCantidad() == 0) {
            plan.getPlanItems().remove(planItem);
            planItemRepositorio.delete(planItem);
        } else {
            planItemRepositorio.save(planItem);
        }

        itemRepositorio.save(item);
    }

    public List<PlanItem> obtenerItemsDePlan(Long planId) {
        return planItemRepositorio.findByPlanId(planId);
    }
}


