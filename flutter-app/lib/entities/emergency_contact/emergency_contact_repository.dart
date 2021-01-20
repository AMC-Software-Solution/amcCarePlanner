import 'package:amcCarePlanner/entities/emergency_contact/emergency_contact_model.dart';
import 'package:amcCarePlanner/shared/repository/http_utils.dart';
import 'package:dart_json_mapper/dart_json_mapper.dart';

class EmergencyContactRepository {
    EmergencyContactRepository();
  
  static final String uriEndpoint = '/emergency-contacts';

  Future<List<EmergencyContact>> getAllEmergencyContacts() async {
    final allEmergencyContactsRequest = await HttpUtils.getRequest(uriEndpoint);
    return JsonMapper.deserialize<List<EmergencyContact>>(allEmergencyContactsRequest.body);
  }

  Future<EmergencyContact> getEmergencyContact(int id) async {
    final emergencyContactRequest = await HttpUtils.getRequest('$uriEndpoint/$id');
    return JsonMapper.deserialize<EmergencyContact>(emergencyContactRequest.body);
  }

  Future<EmergencyContact> create(EmergencyContact emergencyContact) async {
    final emergencyContactRequest = await HttpUtils.postRequest('$uriEndpoint', emergencyContact);
    return JsonMapper.deserialize<EmergencyContact>(emergencyContactRequest.body);
  }

  Future<EmergencyContact> update(EmergencyContact emergencyContact) async {
    final emergencyContactRequest = await HttpUtils.putRequest('$uriEndpoint', emergencyContact);
    return JsonMapper.deserialize<EmergencyContact>(emergencyContactRequest.body);
  }

  Future<void> delete(int id) async {
    final emergencyContactRequest = await HttpUtils.deleteRequest('$uriEndpoint/$id');
  }
}
