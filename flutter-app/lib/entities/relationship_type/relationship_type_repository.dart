import 'package:amcCarePlanner/entities/relationship_type/relationship_type_model.dart';
import 'package:amcCarePlanner/shared/repository/http_utils.dart';
import 'package:dart_json_mapper/dart_json_mapper.dart';

class RelationshipTypeRepository {
    RelationshipTypeRepository();
  
  static final String uriEndpoint = '/relationship-types';

  Future<List<RelationshipType>> getAllRelationshipTypes() async {
    final allRelationshipTypesRequest = await HttpUtils.getRequest(uriEndpoint);
    return JsonMapper.deserialize<List<RelationshipType>>(allRelationshipTypesRequest.body);
  }

  Future<RelationshipType> getRelationshipType(int id) async {
    final relationshipTypeRequest = await HttpUtils.getRequest('$uriEndpoint/$id');
    return JsonMapper.deserialize<RelationshipType>(relationshipTypeRequest.body);
  }

  Future<RelationshipType> create(RelationshipType relationshipType) async {
    final relationshipTypeRequest = await HttpUtils.postRequest('$uriEndpoint', relationshipType);
    return JsonMapper.deserialize<RelationshipType>(relationshipTypeRequest.body);
  }

  Future<RelationshipType> update(RelationshipType relationshipType) async {
    final relationshipTypeRequest = await HttpUtils.putRequest('$uriEndpoint', relationshipType);
    return JsonMapper.deserialize<RelationshipType>(relationshipTypeRequest.body);
  }

  Future<void> delete(int id) async {
    final relationshipTypeRequest = await HttpUtils.deleteRequest('$uriEndpoint/$id');
  }
}
