import 'package:amcCarePlanner/entities/service_user_contact/service_user_contact_model.dart';
import 'package:amcCarePlanner/shared/repository/http_utils.dart';
import 'package:dart_json_mapper/dart_json_mapper.dart';

class ServiceUserContactRepository {
    ServiceUserContactRepository();
  
  static final String uriEndpoint = '/service-user-contacts';

  Future<List<ServiceUserContact>> getAllServiceUserContacts() async {
    final allServiceUserContactsRequest = await HttpUtils.getRequest(uriEndpoint);
    return JsonMapper.deserialize<List<ServiceUserContact>>(allServiceUserContactsRequest.body);
  }

  Future<ServiceUserContact> getServiceUserContact(int id) async {
    final serviceUserContactRequest = await HttpUtils.getRequest('$uriEndpoint/$id');
    return JsonMapper.deserialize<ServiceUserContact>(serviceUserContactRequest.body);
  }

  Future<ServiceUserContact> create(ServiceUserContact serviceUserContact) async {
    final serviceUserContactRequest = await HttpUtils.postRequest('$uriEndpoint', serviceUserContact);
    return JsonMapper.deserialize<ServiceUserContact>(serviceUserContactRequest.body);
  }

  Future<ServiceUserContact> update(ServiceUserContact serviceUserContact) async {
    final serviceUserContactRequest = await HttpUtils.putRequest('$uriEndpoint', serviceUserContact);
    return JsonMapper.deserialize<ServiceUserContact>(serviceUserContactRequest.body);
  }

  Future<void> delete(int id) async {
    final serviceUserContactRequest = await HttpUtils.deleteRequest('$uriEndpoint/$id');
  }
}
