import 'package:amcCarePlanner/entities/eligibility/eligibility_model.dart';
import 'package:amcCarePlanner/shared/repository/http_utils.dart';
import 'package:dart_json_mapper/dart_json_mapper.dart';

class EligibilityRepository {
    EligibilityRepository();
  
  static final String uriEndpoint = '/eligibilities';

  Future<List<Eligibility>> getAllEligibilities() async {
    final allEligibilitiesRequest = await HttpUtils.getRequest(uriEndpoint);
    return JsonMapper.deserialize<List<Eligibility>>(allEligibilitiesRequest.body);
  }

  Future<Eligibility> getEligibility(int id) async {
    final eligibilityRequest = await HttpUtils.getRequest('$uriEndpoint/$id');
    return JsonMapper.deserialize<Eligibility>(eligibilityRequest.body);
  }

  Future<Eligibility> create(Eligibility eligibility) async {
    final eligibilityRequest = await HttpUtils.postRequest('$uriEndpoint', eligibility);
    return JsonMapper.deserialize<Eligibility>(eligibilityRequest.body);
  }

  Future<Eligibility> update(Eligibility eligibility) async {
    final eligibilityRequest = await HttpUtils.putRequest('$uriEndpoint', eligibility);
    return JsonMapper.deserialize<Eligibility>(eligibilityRequest.body);
  }

  Future<void> delete(int id) async {
    final eligibilityRequest = await HttpUtils.deleteRequest('$uriEndpoint/$id');
  }
}
