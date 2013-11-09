package com.agileEAP.workflow.definition;

import org.eclipse.persistence.config.DescriptorCustomizer;
import org.eclipse.persistence.descriptors.ClassDescriptor;

public class ActivityCustomizer implements DescriptorCustomizer {
    @Override
    public void customize(ClassDescriptor descriptor) throws Exception {
        descriptor.getInheritancePolicy().setClassIndicatorFieldName("@activityType");
    }
}
