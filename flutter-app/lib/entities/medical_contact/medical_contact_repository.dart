import 'package:amcCarePlanner/entities/medical_contact/medical_contact_model.dart';
import 'package:amcCarePlanner/shared/repository/http_utils.dart';
import 'package:dart_json_mapper/dart_json_mapper.dart';

class MedicalContactRepository {
    MedicalContactRepository();
  
  static final String uriEndpoint = '/medical-contacts';

  Future<List<MedicalContact>> getAllMedicalContacts() async {
    final allMedicalContactsRequest = await HttpUtils.getRequest(uriEndpoint);
    return JsonMapper.deserialize<List<MedicalContact>>(allMedicalContactsRequest.body);
  }

  Future<MedicalContact> getMedicalContact(int id) async {
    final medicalContactRequest = await HttpUtils.getRequest('$uriEndpoint/$id');
    return JsonMapper.deserialize<MedicalContact>(medicalContactRequest.body);
  }

  Future<MedicalContact> create(MedicalContact medicalContact) async {
    final medicalContactRequest = await HttpUtils.postRequest('$uriEndpoint', medicalContact);
    return JsonMapper.deserialize<MedicalContact>(medicalContactRequest.body);
  }

  Future<MedicalContact> update(MedicalContact medicalContact) async {
    final medicalContactRequest = await HttpUtils.putRequest('$uriEndpoint', medicalContact);
    return JsonMapper.deserialize<MedicalContact>(medicalContactRequest.body);
  }

  Future<void> delete(int id) async {
    final medicalContactRequest = await HttpUtils.deleteRequest('$uriEndpoint/$id');
  }
}
