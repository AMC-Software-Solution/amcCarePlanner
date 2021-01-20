import 'package:amcCarePlanner/entities/carer_service_user_relation/carer_service_user_relation_model.dart';
import 'package:amcCarePlanner/shared/repository/http_utils.dart';
import 'package:dart_json_mapper/dart_json_mapper.dart';

class CarerServiceUserRelationRepository {
    CarerServiceUserRelationRepository();
  
  static final String uriEndpoint = '/carer-service-user-relations';

  Future<List<CarerServiceUserRelation>> getAllCarerServiceUserRelations() async {
    final allCarerServiceUserRelationsRequest = await HttpUtils.getRequest(uriEndpoint);
    return JsonMapper.deserialize<List<CarerServiceUserRelation>>(allCarerServiceUserRelationsRequest.body);
  }

  Future<CarerServiceUserRelation> getCarerServiceUserRelation(int id) async {
    final carerServiceUserRelationRequest = await HttpUtils.getRequest('$uriEndpoint/$id');
    return JsonMapper.deserialize<CarerServiceUserRelation>(carerServiceUserRelationRequest.body);
  }

  Future<CarerServiceUserRelation> create(CarerServiceUserRelation carerServiceUserRelation) async {
    final carerServiceUserRelationRequest = await HttpUtils.postRequest('$uriEndpoint', carerServiceUserRelation);
    return JsonMapper.deserialize<CarerServiceUserRelation>(carerServiceUserRelationRequest.body);
  }

  Future<CarerServiceUserRelation> update(CarerServiceUserRelation carerServiceUserRelation) async {
    final carerServiceUserRelationRequest = await HttpUtils.putRequest('$uriEndpoint', carerServiceUserRelation);
    return JsonMapper.deserialize<CarerServiceUserRelation>(carerServiceUserRelationRequest.body);
  }

  Future<void> delete(int id) async {
    final carerServiceUserRelationRequest = await HttpUtils.deleteRequest('$uriEndpoint/$id');
  }
}
