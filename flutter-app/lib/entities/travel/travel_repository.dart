import 'package:amcCarePlanner/entities/travel/travel_model.dart';
import 'package:amcCarePlanner/shared/repository/http_utils.dart';
import 'package:dart_json_mapper/dart_json_mapper.dart';

class TravelRepository {
    TravelRepository();
  
  static final String uriEndpoint = '/travels';

  Future<List<Travel>> getAllTravels() async {
    final allTravelsRequest = await HttpUtils.getRequest(uriEndpoint);
    return JsonMapper.deserialize<List<Travel>>(allTravelsRequest.body);
  }

  Future<Travel> getTravel(int id) async {
    final travelRequest = await HttpUtils.getRequest('$uriEndpoint/$id');
    return JsonMapper.deserialize<Travel>(travelRequest.body);
  }

  Future<Travel> create(Travel travel) async {
    final travelRequest = await HttpUtils.postRequest('$uriEndpoint', travel);
    return JsonMapper.deserialize<Travel>(travelRequest.body);
  }

  Future<Travel> update(Travel travel) async {
    final travelRequest = await HttpUtils.putRequest('$uriEndpoint', travel);
    return JsonMapper.deserialize<Travel>(travelRequest.body);
  }

  Future<void> delete(int id) async {
    final travelRequest = await HttpUtils.deleteRequest('$uriEndpoint/$id');
  }
}
