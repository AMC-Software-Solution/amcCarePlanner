import 'package:amcCarePlanner/entities/service_order/service_order_model.dart';
import 'package:amcCarePlanner/shared/repository/http_utils.dart';
import 'package:dart_json_mapper/dart_json_mapper.dart';

class ServiceOrderRepository {
    ServiceOrderRepository();
  
  static final String uriEndpoint = '/service-orders';

  Future<List<ServiceOrder>> getAllServiceOrders() async {
    final allServiceOrdersRequest = await HttpUtils.getRequest(uriEndpoint);
    return JsonMapper.deserialize<List<ServiceOrder>>(allServiceOrdersRequest.body);
  }

  Future<ServiceOrder> getServiceOrder(int id) async {
    final serviceOrderRequest = await HttpUtils.getRequest('$uriEndpoint/$id');
    return JsonMapper.deserialize<ServiceOrder>(serviceOrderRequest.body);
  }

  Future<ServiceOrder> create(ServiceOrder serviceOrder) async {
    final serviceOrderRequest = await HttpUtils.postRequest('$uriEndpoint', serviceOrder);
    return JsonMapper.deserialize<ServiceOrder>(serviceOrderRequest.body);
  }

  Future<ServiceOrder> update(ServiceOrder serviceOrder) async {
    final serviceOrderRequest = await HttpUtils.putRequest('$uriEndpoint', serviceOrder);
    return JsonMapper.deserialize<ServiceOrder>(serviceOrderRequest.body);
  }

  Future<void> delete(int id) async {
    final serviceOrderRequest = await HttpUtils.deleteRequest('$uriEndpoint/$id');
  }
}
