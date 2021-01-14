import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IServiceOrder, defaultValue } from 'app/shared/model/service-order.model';

export const ACTION_TYPES = {
  FETCH_SERVICEORDER_LIST: 'serviceOrder/FETCH_SERVICEORDER_LIST',
  FETCH_SERVICEORDER: 'serviceOrder/FETCH_SERVICEORDER',
  CREATE_SERVICEORDER: 'serviceOrder/CREATE_SERVICEORDER',
  UPDATE_SERVICEORDER: 'serviceOrder/UPDATE_SERVICEORDER',
  DELETE_SERVICEORDER: 'serviceOrder/DELETE_SERVICEORDER',
  RESET: 'serviceOrder/RESET',
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IServiceOrder>,
  entity: defaultValue,
  updating: false,
  totalItems: 0,
  updateSuccess: false,
};

export type ServiceOrderState = Readonly<typeof initialState>;

// Reducer

export default (state: ServiceOrderState = initialState, action): ServiceOrderState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_SERVICEORDER_LIST):
    case REQUEST(ACTION_TYPES.FETCH_SERVICEORDER):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true,
      };
    case REQUEST(ACTION_TYPES.CREATE_SERVICEORDER):
    case REQUEST(ACTION_TYPES.UPDATE_SERVICEORDER):
    case REQUEST(ACTION_TYPES.DELETE_SERVICEORDER):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true,
      };
    case FAILURE(ACTION_TYPES.FETCH_SERVICEORDER_LIST):
    case FAILURE(ACTION_TYPES.FETCH_SERVICEORDER):
    case FAILURE(ACTION_TYPES.CREATE_SERVICEORDER):
    case FAILURE(ACTION_TYPES.UPDATE_SERVICEORDER):
    case FAILURE(ACTION_TYPES.DELETE_SERVICEORDER):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload,
      };
    case SUCCESS(ACTION_TYPES.FETCH_SERVICEORDER_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data,
        totalItems: parseInt(action.payload.headers['x-total-count'], 10),
      };
    case SUCCESS(ACTION_TYPES.FETCH_SERVICEORDER):
      return {
        ...state,
        loading: false,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.CREATE_SERVICEORDER):
    case SUCCESS(ACTION_TYPES.UPDATE_SERVICEORDER):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.DELETE_SERVICEORDER):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: {},
      };
    case ACTION_TYPES.RESET:
      return {
        ...initialState,
      };
    default:
      return state;
  }
};

const apiUrl = 'api/v1';

// Actions

export const getEntities: ICrudGetAllAction<IServiceOrder> = (page, size, sort) => {
  const requestUrl = `${apiUrl}/get-all-service-orders-by-client-id${sort ? `?page=${page}&size=${size}&sort=${sort}` : ''}`;
  return {
    type: ACTION_TYPES.FETCH_SERVICEORDER_LIST,
    payload: axios.get<IServiceOrder>(`${requestUrl}${sort ? '&' : '?'}cacheBuster=${new Date().getTime()}`),
  };
};

export const getEntity: ICrudGetAction<IServiceOrder> = id => {
  const requestUrl = `${apiUrl}/get-service-order-by-client-id/${id}`;
  return {
    type: ACTION_TYPES.FETCH_SERVICEORDER,
    payload: axios.get<IServiceOrder>(requestUrl),
  };
};

export const createEntity: ICrudPutAction<IServiceOrder> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_SERVICEORDER,
    payload: axios.post(apiUrl + '/create-service-order-by-client-id', cleanEntity(entity)),
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IServiceOrder> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_SERVICEORDER,
    payload: axios.put(apiUrl + '/update-service-order-by-client-id', cleanEntity(entity)),
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<IServiceOrder> = id => async dispatch => {
  const requestUrl = `${apiUrl}/delete-service-order-by-client-id/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_SERVICEORDER,
    payload: axios.delete(requestUrl),
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET,
});
