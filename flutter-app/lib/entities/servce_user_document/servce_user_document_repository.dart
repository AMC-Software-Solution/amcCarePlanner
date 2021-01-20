import 'package:amcCarePlanner/entities/servce_user_document/servce_user_document_model.dart';
import 'package:amcCarePlanner/shared/repository/http_utils.dart';
import 'package:dart_json_mapper/dart_json_mapper.dart';

class ServceUserDocumentRepository {
    ServceUserDocumentRepository();
  
  static final String uriEndpoint = '/servce-user-documents';

  Future<List<ServceUserDocument>> getAllServceUserDocuments() async {
    final allServceUserDocumentsRequest = await HttpUtils.getRequest(uriEndpoint);
    return JsonMapper.deserialize<List<ServceUserDocument>>(allServceUserDocumentsRequest.body);
  }

  Future<ServceUserDocument> getServceUserDocument(int id) async {
    final servceUserDocumentRequest = await HttpUtils.getRequest('$uriEndpoint/$id');
    return JsonMapper.deserialize<ServceUserDocument>(servceUserDocumentRequest.body);
  }

  Future<ServceUserDocument> create(ServceUserDocument servceUserDocument) async {
    final servceUserDocumentRequest = await HttpUtils.postRequest('$uriEndpoint', servceUserDocument);
    return JsonMapper.deserialize<ServceUserDocument>(servceUserDocumentRequest.body);
  }

  Future<ServceUserDocument> update(ServceUserDocument servceUserDocument) async {
    final servceUserDocumentRequest = await HttpUtils.putRequest('$uriEndpoint', servceUserDocument);
    return JsonMapper.deserialize<ServceUserDocument>(servceUserDocumentRequest.body);
  }

  Future<void> delete(int id) async {
    final servceUserDocumentRequest = await HttpUtils.deleteRequest('$uriEndpoint/$id');
  }
}
