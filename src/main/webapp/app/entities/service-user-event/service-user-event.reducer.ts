import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IServiceUserEvent, defaultValue } from 'app/shared/model/service-user-event.model';

export const ACTION_TYPES = {
  FETCH_SERVICEUSEREVENT_LIST: 'serviceUserEvent/FETCH_SERVICEUSEREVENT_LIST',
  FETCH_SERVICEUSEREVENT: 'serviceUserEvent/FETCH_SERVICEUSEREVENT',
  CREATE_SERVICEUSEREVENT: 'serviceUserEvent/CREATE_SERVICEUSEREVENT',
  UPDATE_SERVICEUSEREVENT: 'serviceUserEvent/UPDATE_SERVICEUSEREVENT',
  DELETE_SERVICEUSEREVENT: 'serviceUserEvent/DELETE_SERVICEUSEREVENT',
  RESET: 'serviceUserEvent/RESET',
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IServiceUserEvent>,
  entity: defaultValue,
  updating: false,
  totalItems: 0,
  updateSuccess: false,
};

export type ServiceUserEventState = Readonly<typeof initialState>;

// Reducer

export default (state: ServiceUserEventState = initialState, action): ServiceUserEventState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_SERVICEUSEREVENT_LIST):
    case REQUEST(ACTION_TYPES.FETCH_SERVICEUSEREVENT):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true,
      };
    case REQUEST(ACTION_TYPES.CREATE_SERVICEUSEREVENT):
    case REQUEST(ACTION_TYPES.UPDATE_SERVICEUSEREVENT):
    case REQUEST(ACTION_TYPES.DELETE_SERVICEUSEREVENT):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true,
      };
    case FAILURE(ACTION_TYPES.FETCH_SERVICEUSEREVENT_LIST):
    case FAILURE(ACTION_TYPES.FETCH_SERVICEUSEREVENT):
    case FAILURE(ACTION_TYPES.CREATE_SERVICEUSEREVENT):
    case FAILURE(ACTION_TYPES.UPDATE_SERVICEUSEREVENT):
    case FAILURE(ACTION_TYPES.DELETE_SERVICEUSEREVENT):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload,
      };
    case SUCCESS(ACTION_TYPES.FETCH_SERVICEUSEREVENT_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data,
        totalItems: parseInt(action.payload.headers['x-total-count'], 10),
      };
    case SUCCESS(ACTION_TYPES.FETCH_SERVICEUSEREVENT):
      return {
        ...state,
        loading: false,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.CREATE_SERVICEUSEREVENT):
    case SUCCESS(ACTION_TYPES.UPDATE_SERVICEUSEREVENT):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.DELETE_SERVICEUSEREVENT):
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

const apiUrl = 'api/service-user-events';

// Actions

export const getEntities: ICrudGetAllAction<IServiceUserEvent> = (page, size, sort) => {
  const requestUrl = `${apiUrl}${sort ? `?page=${page}&size=${size}&sort=${sort}` : ''}`;
  return {
    type: ACTION_TYPES.FETCH_SERVICEUSEREVENT_LIST,
    payload: axios.get<IServiceUserEvent>(`${requestUrl}${sort ? '&' : '?'}cacheBuster=${new Date().getTime()}`),
  };
};

export const getEntity: ICrudGetAction<IServiceUserEvent> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_SERVICEUSEREVENT,
    payload: axios.get<IServiceUserEvent>(requestUrl),
  };
};

export const createEntity: ICrudPutAction<IServiceUserEvent> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_SERVICEUSEREVENT,
    payload: axios.post(apiUrl, cleanEntity(entity)),
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IServiceUserEvent> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_SERVICEUSEREVENT,
    payload: axios.put(apiUrl, cleanEntity(entity)),
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<IServiceUserEvent> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_SERVICEUSEREVENT,
    payload: axios.delete(requestUrl),
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET,
});
