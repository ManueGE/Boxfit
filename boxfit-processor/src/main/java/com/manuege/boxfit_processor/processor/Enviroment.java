package com.manuege.boxfit_processor.processor;

import javax.annotation.processing.ProcessingEnvironment;

/**
 * Created by Manu on 1/2/18.
 */

public class Enviroment {
    static ProcessingEnvironment environment;

    static void setEnvironment(ProcessingEnvironment environment) {
        Enviroment.environment = environment;
    }

    public static ProcessingEnvironment getEnvironment() {
        return environment;
    }
}
