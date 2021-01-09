import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IServiceUser, defaultValue } from 'app/shared/model/service-user.model';

export const ACTION_TYPES = {
  FETCH_SERVICEUSER_LIST: 'serviceUser/FETCH_SERVICEUSER_LIST',
  FETCH_SERVICEUSER: 'serviceUser/FETCH_SERVICEUSER',
  CREATE_SERVICEUSER: 'serviceUser/CREATE_SERVICEUSER',
  UPDATE_SERVICEUSER: 'serviceUser/UPDATE_SERVICEUSER',
  DELETE_SERVICEUSER: 'serviceUser/DELETE_SERVICEUSER',
  SET_BLOB: 'serviceUser/SET_BLOB',
  RESET: 'serviceUser/RESET',
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IServiceUser>,
  entity: defaultValue,
  updating: false,
  totalItems: 0,
  updateSuccess: false,
};

export type ServiceUserState = Readonly<typeof initialState>;

// Reducer

export default (state: ServiceUserState = initialState, action): ServiceUserState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_SERVICEUSER_LIST):
    case REQUEST(ACTION_TYPES.FETCH_SERVICEUSER):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true,
      };
    case REQUEST(ACTION_TYPES.CREATE_SERVICEUSER):
    case REQUEST(ACTION_TYPES.UPDATE_SERVICEUSER):
    case REQUEST(ACTION_TYPES.DELETE_SERVICEUSER):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true,
      };
    case FAILURE(ACTION_TYPES.FETCH_SERVICEUSER_LIST):
    case FAILURE(ACTION_TYPES.FETCH_SERVICEUSER):
    case FAILURE(ACTION_TYPES.CREATE_SERVICEUSER):
    case FAILURE(ACTION_TYPES.UPDATE_SERVICEUSER):
    case FAILURE(ACTION_TYPES.DELETE_SERVICEUSER):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload,
      };
    case SUCCESS(ACTION_TYPES.FETCH_SERVICEUSER_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data,
        totalItems: parseInt(action.payload.headers['x-total-count'], 10),
      };
    case SUCCESS(ACTION_TYPES.FETCH_SERVICEUSER):
      return {
        ...state,
        loading: false,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.CREATE_SERVICEUSER):
    case SUCCESS(ACTION_TYPES.UPDATE_SERVICEUSER):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.DELETE_SERVICEUSER):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: {},
      };
    case ACTION_TYPES.SET_BLOB: {
      const { name, data, contentType } = action.payload;
      return {
        ...state,
        entity: {
          ...state.entity,
          [name]: data,
          [name + 'ContentType']: contentType,
        },
      };
    }
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

export const getEntities: ICrudGetAllAction<IServiceUser> = (page, size, sort) => {
  const requestUrl = `${apiUrl}/get-all-service-users-by-client-id${sort ? `?page=${page}&size=${size}&sort=${sort}` : ''}`;
  return {
    type: ACTION_TYPES.FETCH_SERVICEUSER_LIST,
    payload: axios.get<IServiceUser>(`${requestUrl}${sort ? '&' : '?'}cacheBuster=${new Date().getTime()}`),
  };
};

export const getEntity: ICrudGetAction<IServiceUser> = id => {
  const requestUrl = `${apiUrl}/get-service-user-by-client-id/${id}`;
  return {
    type: ACTION_TYPES.FETCH_SERVICEUSER,
    payload: axios.get<IServiceUser>(requestUrl),
  };
};

export const createEntity: ICrudPutAction<IServiceUser> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_SERVICEUSER,
    payload: axios.post(apiUrl + '/create-service-user-by-client-id', cleanEntity(entity)),
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IServiceUser> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_SERVICEUSER,
    payload: axios.put(apiUrl + '/update-service-user-by-client-id', cleanEntity(entity)),
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<IServiceUser> = id => async dispatch => {
  const requestUrl = `${apiUrl}/delete-service-user-by-client-id/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_SERVICEUSER,
    payload: axios.delete(requestUrl),
  });
  dispatch(getEntities());
  return result;
};

export const setBlob = (name, data, contentType?) => ({
  type: ACTION_TYPES.SET_BLOB,
  payload: {
    name,
    data,
    contentType,
  },
});

export const reset = () => ({
  type: ACTION_TYPES.RESET,
});
