package org.hit.hradar.domain.evaluation.command.application.port;

public interface CommonCodeValidationService {

    void validate(String groupCode, String code);

    /*나중에 구현*/
    /*@Service
    public class LocalCommonCodeValidationService
            implements CommonCodeValidationService {

        @Override
        public void validate(String groupCode, String code) {
            // DB 조회
        }
    }*/

   /* @Service
    public class RemoteCommonCodeValidationService
            implements CommonCodeValidationService {

        @Override
        public void validate(String groupCode, String code) {
            // REST 호출
        }
    }*/


}
