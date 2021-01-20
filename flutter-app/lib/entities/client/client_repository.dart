import 'package:amcCarePlanner/entities/client/client_model.dart';
import 'package:amcCarePlanner/shared/repository/http_utils.dart';
import 'package:dart_json_mapper/dart_json_mapper.dart';

class ClientRepository {
    ClientRepository();
  
  static final String uriEndpoint = '/clients';

  Future<List<Client>> getAllClients() async {
    final allClientsRequest = await HttpUtils.getRequest(uriEndpoint);
    return JsonMapper.deserialize<List<Client>>(allClientsRequest.body);
  }

  Future<Client> getClient(int id) async {
    final clientRequest = await HttpUtils.getRequest('$uriEndpoint/$id');
    return JsonMapper.deserialize<Client>(clientRequest.body);
  }

  Future<Client> create(Client client) async {
    final clientRequest = await HttpUtils.postRequest('$uriEndpoint', client);
    return JsonMapper.deserialize<Client>(clientRequest.body);
  }

  Future<Client> update(Client client) async {
    final clientRequest = await HttpUtils.putRequest('$uriEndpoint', client);
    return JsonMapper.deserialize<Client>(clientRequest.body);
  }

  Future<void> delete(int id) async {
    final clientRequest = await HttpUtils.deleteRequest('$uriEndpoint/$id');
  }
}
