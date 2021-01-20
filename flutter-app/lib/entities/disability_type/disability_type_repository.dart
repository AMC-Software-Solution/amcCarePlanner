import 'package:amcCarePlanner/entities/disability_type/disability_type_model.dart';
import 'package:amcCarePlanner/shared/repository/http_utils.dart';
import 'package:dart_json_mapper/dart_json_mapper.dart';

class DisabilityTypeRepository {
    DisabilityTypeRepository();
  
  static final String uriEndpoint = '/disability-types';

  Future<List<DisabilityType>> getAllDisabilityTypes() async {
    final allDisabilityTypesRequest = await HttpUtils.getRequest(uriEndpoint);
    return JsonMapper.deserialize<List<DisabilityType>>(allDisabilityTypesRequest.body);
  }

  Future<DisabilityType> getDisabilityType(int id) async {
    final disabilityTypeRequest = await HttpUtils.getRequest('$uriEndpoint/$id');
    return JsonMapper.deserialize<DisabilityType>(disabilityTypeRequest.body);
  }

  Future<DisabilityType> create(DisabilityType disabilityType) async {
    final disabilityTypeRequest = await HttpUtils.postRequest('$uriEndpoint', disabilityType);
    return JsonMapper.deserialize<DisabilityType>(disabilityTypeRequest.body);
  }

  Future<DisabilityType> update(DisabilityType disabilityType) async {
    final disabilityTypeRequest = await HttpUtils.putRequest('$uriEndpoint', disabilityType);
    return JsonMapper.deserialize<DisabilityType>(disabilityTypeRequest.body);
  }

  Future<void> delete(int id) async {
    final disabilityTypeRequest = await HttpUtils.deleteRequest('$uriEndpoint/$id');
  }
}
