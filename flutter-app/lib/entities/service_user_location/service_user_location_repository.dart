import 'package:amcCarePlanner/entities/service_user_location/service_user_location_model.dart';
import 'package:amcCarePlanner/shared/repository/http_utils.dart';
import 'package:dart_json_mapper/dart_json_mapper.dart';

class ServiceUserLocationRepository {
    ServiceUserLocationRepository();
  
  static final String uriEndpoint = '/service-user-locations';

  Future<List<ServiceUserLocation>> getAllServiceUserLocations() async {
    final allServiceUserLocationsRequest = await HttpUtils.getRequest(uriEndpoint);
    return JsonMapper.deserialize<List<ServiceUserLocation>>(allServiceUserLocationsRequest.body);
  }

  Future<ServiceUserLocation> getServiceUserLocation(int id) async {
    final serviceUserLocationRequest = await HttpUtils.getRequest('$uriEndpoint/$id');
    return JsonMapper.deserialize<ServiceUserLocation>(serviceUserLocationRequest.body);
  }

  Future<ServiceUserLocation> create(ServiceUserLocation serviceUserLocation) async {
    final serviceUserLocationRequest = await HttpUtils.postRequest('$uriEndpoint', serviceUserLocation);
    return JsonMapper.deserialize<ServiceUserLocation>(serviceUserLocationRequest.body);
  }

  Future<ServiceUserLocation> update(ServiceUserLocation serviceUserLocation) async {
    final serviceUserLocationRequest = await HttpUtils.putRequest('$uriEndpoint', serviceUserLocation);
    return JsonMapper.deserialize<ServiceUserLocation>(serviceUserLocationRequest.body);
  }

  Future<void> delete(int id) async {
    final serviceUserLocationRequest = await HttpUtils.deleteRequest('$uriEndpoint/$id');
  }
}
