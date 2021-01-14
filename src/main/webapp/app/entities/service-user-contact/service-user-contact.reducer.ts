import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IServiceUserContact, defaultValue } from 'app/shared/model/service-user-contact.model';

export const ACTION_TYPES = {
  FETCH_SERVICEUSERCONTACT_LIST: 'serviceUserContact/FETCH_SERVICEUSERCONTACT_LIST',
  FETCH_SERVICEUSERCONTACT: 'serviceUserContact/FETCH_SERVICEUSERCONTACT',
  CREATE_SERVICEUSERCONTACT: 'serviceUserContact/CREATE_SERVICEUSERCONTACT',
  UPDATE_SERVICEUSERCONTACT: 'serviceUserContact/UPDATE_SERVICEUSERCONTACT',
  DELETE_SERVICEUSERCONTACT: 'serviceUserContact/DELETE_SERVICEUSERCONTACT',
  RESET: 'serviceUserContact/RESET',
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IServiceUserContact>,
  entity: defaultValue,
  updating: false,
  totalItems: 0,
  updateSuccess: false,
};

export type ServiceUserContactState = Readonly<typeof initialState>;

// Reducer

export default (state: ServiceUserContactState = initialState, action): ServiceUserContactState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_SERVICEUSERCONTACT_LIST):
    case REQUEST(ACTION_TYPES.FETCH_SERVICEUSERCONTACT):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true,
      };
    case REQUEST(ACTION_TYPES.CREATE_SERVICEUSERCONTACT):
    case REQUEST(ACTION_TYPES.UPDATE_SERVICEUSERCONTACT):
    case REQUEST(ACTION_TYPES.DELETE_SERVICEUSERCONTACT):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true,
      };
    case FAILURE(ACTION_TYPES.FETCH_SERVICEUSERCONTACT_LIST):
    case FAILURE(ACTION_TYPES.FETCH_SERVICEUSERCONTACT):
    case FAILURE(ACTION_TYPES.CREATE_SERVICEUSERCONTACT):
    case FAILURE(ACTION_TYPES.UPDATE_SERVICEUSERCONTACT):
    case FAILURE(ACTION_TYPES.DELETE_SERVICEUSERCONTACT):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload,
      };
    case SUCCESS(ACTION_TYPES.FETCH_SERVICEUSERCONTACT_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data,
        totalItems: parseInt(action.payload.headers['x-total-count'], 10),
      };
    case SUCCESS(ACTION_TYPES.FETCH_SERVICEUSERCONTACT):
      return {
        ...state,
        loading: false,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.CREATE_SERVICEUSERCONTACT):
    case SUCCESS(ACTION_TYPES.UPDATE_SERVICEUSERCONTACT):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.DELETE_SERVICEUSERCONTACT):
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

export const getEntities: ICrudGetAllAction<IServiceUserContact> = (page, size, sort) => {
  const requestUrl = `${apiUrl}/get-all-service-user-contacts-by-client-id${sort ? `?page=${page}&size=${size}&sort=${sort}` : ''}`;
  return {
    type: ACTION_TYPES.FETCH_SERVICEUSERCONTACT_LIST,
    payload: axios.get<IServiceUserContact>(`${requestUrl}${sort ? '&' : '?'}cacheBuster=${new Date().getTime()}`),
  };
};

export const getEntity: ICrudGetAction<IServiceUserContact> = id => {
  const requestUrl = `${apiUrl}/get-service-user-contact-by-client-id/${id}`;
  return {
    type: ACTION_TYPES.FETCH_SERVICEUSERCONTACT,
    payload: axios.get<IServiceUserContact>(requestUrl),
  };
};

export const createEntity: ICrudPutAction<IServiceUserContact> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_SERVICEUSERCONTACT,
    payload: axios.post(apiUrl + '/create-service-user-contact-by-client-id', cleanEntity(entity)),
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IServiceUserContact> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_SERVICEUSERCONTACT,
    payload: axios.put(apiUrl + '/update-service-user-contact-by-client-id', cleanEntity(entity)),
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<IServiceUserContact> = id => async dispatch => {
  const requestUrl = `${apiUrl}/delete-service-user-contact-by-client-id/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_SERVICEUSERCONTACT,
    payload: axios.delete(requestUrl),
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET,
});
