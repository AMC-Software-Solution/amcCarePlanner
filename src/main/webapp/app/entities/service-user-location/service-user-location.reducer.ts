import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IServiceUserLocation, defaultValue } from 'app/shared/model/service-user-location.model';

export const ACTION_TYPES = {
  FETCH_SERVICEUSERLOCATION_LIST: 'serviceUserLocation/FETCH_SERVICEUSERLOCATION_LIST',
  FETCH_SERVICEUSERLOCATION: 'serviceUserLocation/FETCH_SERVICEUSERLOCATION',
  CREATE_SERVICEUSERLOCATION: 'serviceUserLocation/CREATE_SERVICEUSERLOCATION',
  UPDATE_SERVICEUSERLOCATION: 'serviceUserLocation/UPDATE_SERVICEUSERLOCATION',
  DELETE_SERVICEUSERLOCATION: 'serviceUserLocation/DELETE_SERVICEUSERLOCATION',
  RESET: 'serviceUserLocation/RESET',
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IServiceUserLocation>,
  entity: defaultValue,
  updating: false,
  totalItems: 0,
  updateSuccess: false,
};

export type ServiceUserLocationState = Readonly<typeof initialState>;

// Reducer

export default (state: ServiceUserLocationState = initialState, action): ServiceUserLocationState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_SERVICEUSERLOCATION_LIST):
    case REQUEST(ACTION_TYPES.FETCH_SERVICEUSERLOCATION):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true,
      };
    case REQUEST(ACTION_TYPES.CREATE_SERVICEUSERLOCATION):
    case REQUEST(ACTION_TYPES.UPDATE_SERVICEUSERLOCATION):
    case REQUEST(ACTION_TYPES.DELETE_SERVICEUSERLOCATION):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true,
      };
    case FAILURE(ACTION_TYPES.FETCH_SERVICEUSERLOCATION_LIST):
    case FAILURE(ACTION_TYPES.FETCH_SERVICEUSERLOCATION):
    case FAILURE(ACTION_TYPES.CREATE_SERVICEUSERLOCATION):
    case FAILURE(ACTION_TYPES.UPDATE_SERVICEUSERLOCATION):
    case FAILURE(ACTION_TYPES.DELETE_SERVICEUSERLOCATION):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload,
      };
    case SUCCESS(ACTION_TYPES.FETCH_SERVICEUSERLOCATION_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data,
        totalItems: parseInt(action.payload.headers['x-total-count'], 10),
      };
    case SUCCESS(ACTION_TYPES.FETCH_SERVICEUSERLOCATION):
      return {
        ...state,
        loading: false,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.CREATE_SERVICEUSERLOCATION):
    case SUCCESS(ACTION_TYPES.UPDATE_SERVICEUSERLOCATION):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.DELETE_SERVICEUSERLOCATION):
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

export const getEntities: ICrudGetAllAction<IServiceUserLocation> = (page, size, sort) => {
  const requestUrl = `${apiUrl}/get-all-service-user-locations-by-client-id${sort ? `?page=${page}&size=${size}&sort=${sort}` : ''}`;
  return {
    type: ACTION_TYPES.FETCH_SERVICEUSERLOCATION_LIST,
    payload: axios.get<IServiceUserLocation>(`${requestUrl}${sort ? '&' : '?'}cacheBuster=${new Date().getTime()}`),
  };
};

export const getEntity: ICrudGetAction<IServiceUserLocation> = id => {
  const requestUrl = `${apiUrl}/get-service-user-location-by-client-id/${id}`;
  return {
    type: ACTION_TYPES.FETCH_SERVICEUSERLOCATION,
    payload: axios.get<IServiceUserLocation>(requestUrl),
  };
};

export const createEntity: ICrudPutAction<IServiceUserLocation> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_SERVICEUSERLOCATION,
    payload: axios.post(apiUrl + '/create-service-user-location-by-client-id', cleanEntity(entity)),
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IServiceUserLocation> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_SERVICEUSERLOCATION,
    payload: axios.put(apiUrl + '/update-service-user-location-by-client-id', cleanEntity(entity)),
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<IServiceUserLocation> = id => async dispatch => {
  const requestUrl = `${apiUrl}/delete-service-user-location-by-client-id/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_SERVICEUSERLOCATION,
    payload: axios.delete(requestUrl),
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET,
});
