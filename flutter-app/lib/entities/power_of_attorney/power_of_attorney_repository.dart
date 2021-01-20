import 'package:amcCarePlanner/entities/power_of_attorney/power_of_attorney_model.dart';
import 'package:amcCarePlanner/shared/repository/http_utils.dart';
import 'package:dart_json_mapper/dart_json_mapper.dart';

class PowerOfAttorneyRepository {
    PowerOfAttorneyRepository();
  
  static final String uriEndpoint = '/power-of-attorneys';

  Future<List<PowerOfAttorney>> getAllPowerOfAttorneys() async {
    final allPowerOfAttorneysRequest = await HttpUtils.getRequest(uriEndpoint);
    return JsonMapper.deserialize<List<PowerOfAttorney>>(allPowerOfAttorneysRequest.body);
  }

  Future<PowerOfAttorney> getPowerOfAttorney(int id) async {
    final powerOfAttorneyRequest = await HttpUtils.getRequest('$uriEndpoint/$id');
    return JsonMapper.deserialize<PowerOfAttorney>(powerOfAttorneyRequest.body);
  }

  Future<PowerOfAttorney> create(PowerOfAttorney powerOfAttorney) async {
    final powerOfAttorneyRequest = await HttpUtils.postRequest('$uriEndpoint', powerOfAttorney);
    return JsonMapper.deserialize<PowerOfAttorney>(powerOfAttorneyRequest.body);
  }

  Future<PowerOfAttorney> update(PowerOfAttorney powerOfAttorney) async {
    final powerOfAttorneyRequest = await HttpUtils.putRequest('$uriEndpoint', powerOfAttorney);
    return JsonMapper.deserialize<PowerOfAttorney>(powerOfAttorneyRequest.body);
  }

  Future<void> delete(int id) async {
    final powerOfAttorneyRequest = await HttpUtils.deleteRequest('$uriEndpoint/$id');
  }
}
