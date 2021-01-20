import 'package:amcCarePlanner/entities/document_type/document_type_model.dart';
import 'package:amcCarePlanner/shared/repository/http_utils.dart';
import 'package:dart_json_mapper/dart_json_mapper.dart';

class DocumentTypeRepository {
    DocumentTypeRepository();
  
  static final String uriEndpoint = '/document-types';

  Future<List<DocumentType>> getAllDocumentTypes() async {
    final allDocumentTypesRequest = await HttpUtils.getRequest(uriEndpoint);
    return JsonMapper.deserialize<List<DocumentType>>(allDocumentTypesRequest.body);
  }

  Future<DocumentType> getDocumentType(int id) async {
    final documentTypeRequest = await HttpUtils.getRequest('$uriEndpoint/$id');
    return JsonMapper.deserialize<DocumentType>(documentTypeRequest.body);
  }

  Future<DocumentType> create(DocumentType documentType) async {
    final documentTypeRequest = await HttpUtils.postRequest('$uriEndpoint', documentType);
    return JsonMapper.deserialize<DocumentType>(documentTypeRequest.body);
  }

  Future<DocumentType> update(DocumentType documentType) async {
    final documentTypeRequest = await HttpUtils.putRequest('$uriEndpoint', documentType);
    return JsonMapper.deserialize<DocumentType>(documentTypeRequest.body);
  }

  Future<void> delete(int id) async {
    final documentTypeRequest = await HttpUtils.deleteRequest('$uriEndpoint/$id');
  }
}
