package com.aladin.web.rest.vm;

import org.springframework.cloud.client.ServiceInstance;

import java.util.List;

/**
 * View Model that stores a route managed by the Gateway.
 */
public class RouteVM {

    private String path;

    private String serviceId;

    private List<ServiceInstance> serviceInstances;

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getServiceId() {
        return serviceId;
    }

    public void setServiceId(String serviceId) {
        this.serviceId = serviceId;
    }

    public List<ServiceInstance> getServiceInstances() {
        return serviceInstances;
    }

    public void setServiceInstances(List<ServiceInstance> serviceInstances) {
        this.serviceInstances = serviceInstances;
    }
    public String instanceToString(ServiceInstance serviceInstance) {
        return "ServiceInstance{" +
            "path='" + serviceInstances + '\'' +
            ", serviceId='" + serviceId + '\'' +
            ", serviceInstances='" + serviceInstances + '\'' +
            "}";
    }
    // prettier-ignore
    @Override
    public String toString() {
        return "RouteVM{" +
            "path='" + path + '\'' +
            ", serviceId='" + serviceId + '\'' +
            ", serviceInstances='" + serviceInstances + '\'' +
            "}";
    }
}
