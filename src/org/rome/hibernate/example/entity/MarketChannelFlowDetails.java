package org.rome.hibernate.example.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.io.Serializable;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MarketChannelFlowDetails implements Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String name;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        MarketChannelFlowDetails that = (MarketChannelFlowDetails) o;

        return new EqualsBuilder()
                .appendSuper(super.equals(o))
                .append(id, that.id)
                .append(name, that.name)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .appendSuper(super.hashCode())
                .append(id)
                .append(name)
                .toHashCode();
    }

    @ManyToOne
    @JoinColumn(name="flow_id")
    private FlowDetails flowDetails;


    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("MarketChannelFlowDetails{");
        sb.append("id=").append(id);
        sb.append(", name='").append(name).append('\'');
        if(flowDetails!= null)
            sb.append(", flowDetails=").append(flowDetails.getId());
        else
            sb.append(", flowDetails=").append("null");
        sb.append('}');
        return sb.toString();
    }
}
