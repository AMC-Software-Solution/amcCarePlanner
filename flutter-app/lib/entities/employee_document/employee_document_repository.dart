import 'package:amcCarePlanner/entities/employee_document/employee_document_model.dart';
import 'package:amcCarePlanner/shared/repository/http_utils.dart';
import 'package:dart_json_mapper/dart_json_mapper.dart';

class EmployeeDocumentRepository {
    EmployeeDocumentRepository();
  
  static final String uriEndpoint = '/employee-documents';

  Future<List<EmployeeDocument>> getAllEmployeeDocuments() async {
    final allEmployeeDocumentsRequest = await HttpUtils.getRequest(uriEndpoint);
    return JsonMapper.deserialize<List<EmployeeDocument>>(allEmployeeDocumentsRequest.body);
  }

  Future<EmployeeDocument> getEmployeeDocument(int id) async {
    final employeeDocumentRequest = await HttpUtils.getRequest('$uriEndpoint/$id');
    return JsonMapper.deserialize<EmployeeDocument>(employeeDocumentRequest.body);
  }

  Future<EmployeeDocument> create(EmployeeDocument employeeDocument) async {
    final employeeDocumentRequest = await HttpUtils.postRequest('$uriEndpoint', employeeDocument);
    return JsonMapper.deserialize<EmployeeDocument>(employeeDocumentRequest.body);
  }

  Future<EmployeeDocument> update(EmployeeDocument employeeDocument) async {
    final employeeDocumentRequest = await HttpUtils.putRequest('$uriEndpoint', employeeDocument);
    return JsonMapper.deserialize<EmployeeDocument>(employeeDocumentRequest.body);
  }

  Future<void> delete(int id) async {
    final employeeDocumentRequest = await HttpUtils.deleteRequest('$uriEndpoint/$id');
  }
}
