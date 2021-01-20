import 'package:amcCarePlanner/entities/communication/communication_model.dart';
import 'package:amcCarePlanner/shared/repository/http_utils.dart';
import 'package:dart_json_mapper/dart_json_mapper.dart';

class CommunicationRepository {
    CommunicationRepository();
  
  static final String uriEndpoint = '/communications';

  Future<List<Communication>> getAllCommunications() async {
    final allCommunicationsRequest = await HttpUtils.getRequest(uriEndpoint);
    return JsonMapper.deserialize<List<Communication>>(allCommunicationsRequest.body);
  }

  Future<Communication> getCommunication(int id) async {
    final communicationRequest = await HttpUtils.getRequest('$uriEndpoint/$id');
    return JsonMapper.deserialize<Communication>(communicationRequest.body);
  }

  Future<Communication> create(Communication communication) async {
    final communicationRequest = await HttpUtils.postRequest('$uriEndpoint', communication);
    return JsonMapper.deserialize<Communication>(communicationRequest.body);
  }

  Future<Communication> update(Communication communication) async {
    final communicationRequest = await HttpUtils.putRequest('$uriEndpoint', communication);
    return JsonMapper.deserialize<Communication>(communicationRequest.body);
  }

  Future<void> delete(int id) async {
    final communicationRequest = await HttpUtils.deleteRequest('$uriEndpoint/$id');
  }
}
