package demospringboot.app.web.model;

import brave.Span;
import brave.Tracer;
import brave.internal.Nullable;

import demospringboot.util.ResponseCode;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.springframework.data.domain.Page;


import java.io.Serial;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Objects;
import java.util.stream.Collectors;

import springfox.documentation.spring.web.json.Json;

@ApiModel(description = "Generic response model")
public final class ResponseModel<T> implements Serializable {

    private static final long serialVersionUID = 1L;

    @NonNull
    @Getter
    @ApiModelProperty(value = "Business code XX_YY_# - XX = Module ID, YY = Microservice ID, # = consecutive number", required = true, example = "MD_MS_001")
    private String businessCode;

    @NonNull
    @Getter
    @ApiModelProperty(value = "Message describing the response", required = true, example = "Customer found")
    private String message;

    @NonNull
    @Getter
    @ApiModelProperty(value = "Trace ID for correlating logs and requests", required = true, example = "123e4567-e89b-12d3-a456-426614174000")
    private String traceId;

    @Nullable
    @Getter
    @ApiModelProperty(value = "Data payload of the response, can be null if there is no data to return", required = false, example = "{}")
    private T data;

    @Nullable
    @Getter
    @ApiModelProperty(value = "Metadata about the response, such as pagination info or additional details", required = false, example = "{}")
    private Metadata metadata;

    private ResponseModel() {}

    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    private static class Metadata {

        @ApiModelProperty(value = "Current page number in paginated responses", required = true, example = "1")
        private int page;

        @ApiModelProperty(value = "Number of items per page in paginated responses", required = true, example = "10")
        private int size;

        @ApiModelProperty(value = "Total number of elements available across all pages in paginated responses", required = true, example = "100")
        private long elements;

    }


    public static ICode builder(Tracer tracer){
        //return new ResponseModel.Builder(tracer.currentSpan().context().traceIdString());

        Span currentSpan = tracer.currentSpan();
        String traceId = "";
        if (currentSpan != null) {
            traceId = currentSpan.context().traceIdString();
            System.out.println("Current Trace ID: " + traceId);

            // Example of adding a custom tag to the current span
            //currentSpan.tag("user.action", "performAction was called");
        } else {
            System.out.println("No current span found.");
        }

        return new ResponseModel.Builder(traceId);
    }

    public interface ICode {
        IData code(ResponseCode code, String ... values);
    }

    public interface IData<T> {
        IBuilder data(T data);
    }

    public interface IBuilder {
        ResponseModel build();
    }


    private static class Builder implements ICode, IData, IBuilder{

        private final ResponseModel instance;

        public Builder(String traceId) {
            instance = new ResponseModel();
            instance.traceId = traceId;
        }


        @Override
        public IData code(ResponseCode code, String ... values){
            instance.businessCode = code.name();
            StringBuilder message = new StringBuilder(code.message());

            if (values.length > 0){
                message.append("(");
                message.append(Arrays.asList(values).stream().collect(Collectors.joining(",")));
                message.append(")");
            }
            instance.message = message.toString();
            return this;
        }

        @Override
        public IBuilder data(Object data){
            instance.metadata = new Metadata();
            if(Objects.isNull(data)){
                instance.data = new Json("{}");
            }else if(data instanceof Page){
                Page page = (Page) data;
                instance.data = page.getContent();
                instance.metadata = new Metadata(page.getNumber(), page.getSize(), page.getTotalElements());
            }else{
                instance.data = data;
            }

            return this;
        }

        @Override
        public ResponseModel build() {
            return instance;
        }

    }

}
