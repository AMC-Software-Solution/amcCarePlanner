import 'package:amcCarePlanner/entities/client_document/client_document_model.dart';
import 'package:amcCarePlanner/shared/repository/http_utils.dart';
import 'package:dart_json_mapper/dart_json_mapper.dart';

class ClientDocumentRepository {
    ClientDocumentRepository();
  
  static final String uriEndpoint = '/client-documents';

  Future<List<ClientDocument>> getAllClientDocuments() async {
    final allClientDocumentsRequest = await HttpUtils.getRequest(uriEndpoint);
    return JsonMapper.deserialize<List<ClientDocument>>(allClientDocumentsRequest.body);
  }

  Future<ClientDocument> getClientDocument(int id) async {
    final clientDocumentRequest = await HttpUtils.getRequest('$uriEndpoint/$id');
    return JsonMapper.deserialize<ClientDocument>(clientDocumentRequest.body);
  }

  Future<ClientDocument> create(ClientDocument clientDocument) async {
    final clientDocumentRequest = await HttpUtils.postRequest('$uriEndpoint', clientDocument);
    return JsonMapper.deserialize<ClientDocument>(clientDocumentRequest.body);
  }

  Future<ClientDocument> update(ClientDocument clientDocument) async {
    final clientDocumentRequest = await HttpUtils.putRequest('$uriEndpoint', clientDocument);
    return JsonMapper.deserialize<ClientDocument>(clientDocumentRequest.body);
  }

  Future<void> delete(int id) async {
    final clientDocumentRequest = await HttpUtils.deleteRequest('$uriEndpoint/$id');
  }
}
