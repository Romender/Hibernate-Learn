package org.rome.hibernate.example.repository;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.rome.hibernate.example.SesssionFactoryHelper;
import org.rome.hibernate.example.entity.FlowDetails;
import org.rome.hibernate.example.entity.FlowDetailsHierarchyNodeMap;
import org.rome.hibernate.example.entity.MarketChannelFlowDetails;
import org.rome.hibernate.example.entity.PlanningDetails;
import org.rome.hibernate.example.entity.SeasonCode;

import javax.persistence.EntityManager;
import java.util.List;

@AllArgsConstructor
@Slf4j
public class FlowDetailsRepository {

    private EntityManager entityManager;

    public FlowDetailsRepository(){
        entityManager = SesssionFactoryHelper.instance.createSesssion();
    }

    public SeasonCode createSeasonCode(String name){
        SeasonCode seasonCode = null;
        boolean isSuccess = true;
        try {
            entityManager.getTransaction().begin();
            seasonCode = SeasonCode.builder().name(name).build();
            entityManager.persist(seasonCode);
        } catch (Exception e) {
            isSuccess= false;

        } finally {
            if(isSuccess)
                entityManager.getTransaction().commit();
            else
                entityManager.getTransaction().rollback();

        }
        return seasonCode;
    }


    public PlanningDetails createPlanningDetails(String name){
        PlanningDetails planningDetails = null;
        boolean isSuccess = true;
        try {
            entityManager.getTransaction().begin();
            planningDetails = PlanningDetails.builder().name(name).build();
            entityManager.persist(planningDetails);
        } catch (Exception e) {
            isSuccess= false;

        } finally {
            if(isSuccess)
                entityManager.getTransaction().commit();
            else
                entityManager.getTransaction().rollback();

        }
        return planningDetails;
    }


    public FlowDetails createFlowDetails(String name, PlanningDetails planningDetails, SeasonCode seasonCode,
                                         List<MarketChannelFlowDetails> marketChannelFlowDetails,
                                         List<FlowDetailsHierarchyNodeMap> hierarchyNodeMaps){

        FlowDetails flowDetails = null;
        boolean isSuccess = true;
        try {
            entityManager.getTransaction().begin();
            flowDetails = FlowDetails.builder().name(name)
                    .seasonCode(seasonCode).planningDetails(planningDetails)
                    .build();
            for(FlowDetailsHierarchyNodeMap flowDetailsHierarchyNodeMap : hierarchyNodeMaps)
                flowDetails.addFlowDetailsHierarchyNodeMap(flowDetailsHierarchyNodeMap);
            for(MarketChannelFlowDetails marketChannelFlowDetail : marketChannelFlowDetails)
                flowDetails.addMarketChannelFlowDetails(marketChannelFlowDetail);
            entityManager.persist(flowDetails);
        } catch (Exception e) {
            isSuccess= false;
            log.error(e.getMessage());
        } finally {
            if(isSuccess)
                entityManager.getTransaction().commit();
            else
                entityManager.getTransaction().rollback();

        }
        return flowDetails;
    }

}
