import 'package:amcCarePlanner/entities/extra_data/extra_data_model.dart';
import 'package:amcCarePlanner/shared/repository/http_utils.dart';
import 'package:dart_json_mapper/dart_json_mapper.dart';

class ExtraDataRepository {
    ExtraDataRepository();
  
  static final String uriEndpoint = '/extra-data';

  Future<List<ExtraData>> getAllExtraData() async {
    final allExtraDataRequest = await HttpUtils.getRequest(uriEndpoint);
    return JsonMapper.deserialize<List<ExtraData>>(allExtraDataRequest.body);
  }

  Future<ExtraData> getExtraData(int id) async {
    final extraDataRequest = await HttpUtils.getRequest('$uriEndpoint/$id');
    return JsonMapper.deserialize<ExtraData>(extraDataRequest.body);
  }

  Future<ExtraData> create(ExtraData extraData) async {
    final extraDataRequest = await HttpUtils.postRequest('$uriEndpoint', extraData);
    return JsonMapper.deserialize<ExtraData>(extraDataRequest.body);
  }

  Future<ExtraData> update(ExtraData extraData) async {
    final extraDataRequest = await HttpUtils.putRequest('$uriEndpoint', extraData);
    return JsonMapper.deserialize<ExtraData>(extraDataRequest.body);
  }

  Future<void> delete(int id) async {
    final extraDataRequest = await HttpUtils.deleteRequest('$uriEndpoint/$id');
  }
}
