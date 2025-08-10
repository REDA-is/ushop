package org.reda.ushop.services;

import ai.onnxruntime.*;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.FloatBuffer;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class AiModelService {

    private final OrtEnvironment env;
    private final OrtSession session;
    private final List<String> labels;

    public AiModelService() throws Exception {
        this.env = OrtEnvironment.getEnvironment();
        this.session = loadModel();
        this.labels = loadLabels();
    }

    private OrtSession loadModel() throws Exception {
        try (InputStream modelStream = new ClassPathResource("model/product_recommender.onnx").getInputStream()) {
            OrtSession.SessionOptions options = new OrtSession.SessionOptions();
            return env.createSession(modelStream.readAllBytes(), options);
        }
    }

    private List<String> loadLabels() throws Exception {
        ClassPathResource labelsPath = new ClassPathResource("model/labels.txt");
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(labelsPath.getInputStream()))) {
            return reader.lines().map(String::trim).filter(l -> !l.isEmpty()).collect(Collectors.toList());
        }
    }

    public List<String> predict(float wantsPump, float wantsToDry, float isTired, float wantsToBulk) throws Exception {
        float[] inputData = new float[]{wantsPump, wantsToDry, isTired, wantsToBulk};
        FloatBuffer fb = FloatBuffer.wrap(inputData);

        Map<String, OnnxTensor> inputs = new HashMap<>();
        inputs.put("input", OnnxTensor.createTensor(env, fb, new long[]{1, 4}));

        try (OrtSession.Result result = session.run(inputs)) {
            long[][] prediction = (long[][]) result.get(0).getValue();

            List<String> recommended = new ArrayList<>();
            for (int i = 0; i < prediction[0].length; i++) {
                if (prediction[0][i] == 1) {
                    recommended.add(labels.get(i));
                }
            }

            return recommended;
        }
    }
}
