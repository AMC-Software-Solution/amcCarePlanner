import 'package:amcCarePlanner/entities/eligibility_type/eligibility_type_model.dart';
import 'package:amcCarePlanner/shared/repository/http_utils.dart';
import 'package:dart_json_mapper/dart_json_mapper.dart';

class EligibilityTypeRepository {
    EligibilityTypeRepository();
  
  static final String uriEndpoint = '/eligibility-types';

  Future<List<EligibilityType>> getAllEligibilityTypes() async {
    final allEligibilityTypesRequest = await HttpUtils.getRequest(uriEndpoint);
    return JsonMapper.deserialize<List<EligibilityType>>(allEligibilityTypesRequest.body);
  }

  Future<EligibilityType> getEligibilityType(int id) async {
    final eligibilityTypeRequest = await HttpUtils.getRequest('$uriEndpoint/$id');
    return JsonMapper.deserialize<EligibilityType>(eligibilityTypeRequest.body);
  }

  Future<EligibilityType> create(EligibilityType eligibilityType) async {
    final eligibilityTypeRequest = await HttpUtils.postRequest('$uriEndpoint', eligibilityType);
    return JsonMapper.deserialize<EligibilityType>(eligibilityTypeRequest.body);
  }

  Future<EligibilityType> update(EligibilityType eligibilityType) async {
    final eligibilityTypeRequest = await HttpUtils.putRequest('$uriEndpoint', eligibilityType);
    return JsonMapper.deserialize<EligibilityType>(eligibilityTypeRequest.body);
  }

  Future<void> delete(int id) async {
    final eligibilityTypeRequest = await HttpUtils.deleteRequest('$uriEndpoint/$id');
  }
}
