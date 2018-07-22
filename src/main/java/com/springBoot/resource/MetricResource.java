package com.springBoot.resource;

import com.springBoot.service.MetricService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping(path = "/status")
public class MetricResource {


    @RequestMapping(value = "/metric", method = RequestMethod.GET)
    @ResponseBody
    public Map getStatusMetric() {
        MetricService metricService = new MetricService();
        System.out.println("here in status metric controller");
        return metricService.getFullMetric();
    }
}
