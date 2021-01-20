import 'package:amcCarePlanner/entities/consent/consent_model.dart';
import 'package:amcCarePlanner/shared/repository/http_utils.dart';
import 'package:dart_json_mapper/dart_json_mapper.dart';

class ConsentRepository {
    ConsentRepository();
  
  static final String uriEndpoint = '/consents';

  Future<List<Consent>> getAllConsents() async {
    final allConsentsRequest = await HttpUtils.getRequest(uriEndpoint);
    return JsonMapper.deserialize<List<Consent>>(allConsentsRequest.body);
  }

  Future<Consent> getConsent(int id) async {
    final consentRequest = await HttpUtils.getRequest('$uriEndpoint/$id');
    return JsonMapper.deserialize<Consent>(consentRequest.body);
  }

  Future<Consent> create(Consent consent) async {
    final consentRequest = await HttpUtils.postRequest('$uriEndpoint', consent);
    return JsonMapper.deserialize<Consent>(consentRequest.body);
  }

  Future<Consent> update(Consent consent) async {
    final consentRequest = await HttpUtils.putRequest('$uriEndpoint', consent);
    return JsonMapper.deserialize<Consent>(consentRequest.body);
  }

  Future<void> delete(int id) async {
    final consentRequest = await HttpUtils.deleteRequest('$uriEndpoint/$id');
  }
}
