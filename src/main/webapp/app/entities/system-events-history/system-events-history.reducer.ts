import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { ISystemEventsHistory, defaultValue } from 'app/shared/model/system-events-history.model';

export const ACTION_TYPES = {
  FETCH_SYSTEMEVENTSHISTORY_LIST: 'systemEventsHistory/FETCH_SYSTEMEVENTSHISTORY_LIST',
  FETCH_SYSTEMEVENTSHISTORY: 'systemEventsHistory/FETCH_SYSTEMEVENTSHISTORY',
  CREATE_SYSTEMEVENTSHISTORY: 'systemEventsHistory/CREATE_SYSTEMEVENTSHISTORY',
  UPDATE_SYSTEMEVENTSHISTORY: 'systemEventsHistory/UPDATE_SYSTEMEVENTSHISTORY',
  DELETE_SYSTEMEVENTSHISTORY: 'systemEventsHistory/DELETE_SYSTEMEVENTSHISTORY',
  RESET: 'systemEventsHistory/RESET',
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<ISystemEventsHistory>,
  entity: defaultValue,
  updating: false,
  totalItems: 0,
  updateSuccess: false,
};

export type SystemEventsHistoryState = Readonly<typeof initialState>;

// Reducer

export default (state: SystemEventsHistoryState = initialState, action): SystemEventsHistoryState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_SYSTEMEVENTSHISTORY_LIST):
    case REQUEST(ACTION_TYPES.FETCH_SYSTEMEVENTSHISTORY):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true,
      };
    case REQUEST(ACTION_TYPES.CREATE_SYSTEMEVENTSHISTORY):
    case REQUEST(ACTION_TYPES.UPDATE_SYSTEMEVENTSHISTORY):
    case REQUEST(ACTION_TYPES.DELETE_SYSTEMEVENTSHISTORY):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true,
      };
    case FAILURE(ACTION_TYPES.FETCH_SYSTEMEVENTSHISTORY_LIST):
    case FAILURE(ACTION_TYPES.FETCH_SYSTEMEVENTSHISTORY):
    case FAILURE(ACTION_TYPES.CREATE_SYSTEMEVENTSHISTORY):
    case FAILURE(ACTION_TYPES.UPDATE_SYSTEMEVENTSHISTORY):
    case FAILURE(ACTION_TYPES.DELETE_SYSTEMEVENTSHISTORY):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload,
      };
    case SUCCESS(ACTION_TYPES.FETCH_SYSTEMEVENTSHISTORY_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data,
        totalItems: parseInt(action.payload.headers['x-total-count'], 10),
      };
    case SUCCESS(ACTION_TYPES.FETCH_SYSTEMEVENTSHISTORY):
      return {
        ...state,
        loading: false,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.CREATE_SYSTEMEVENTSHISTORY):
    case SUCCESS(ACTION_TYPES.UPDATE_SYSTEMEVENTSHISTORY):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.DELETE_SYSTEMEVENTSHISTORY):
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

export const getEntities: ICrudGetAllAction<ISystemEventsHistory> = (page, size, sort) => {
  const requestUrl = `${apiUrl}/get-all-system-events-histories-by-client-id${sort ? `?page=${page}&size=${size}&sort=${sort}` : ''}`;
  return {
    type: ACTION_TYPES.FETCH_SYSTEMEVENTSHISTORY_LIST,
    payload: axios.get<ISystemEventsHistory>(`${requestUrl}${sort ? '&' : '?'}cacheBuster=${new Date().getTime()}`),
  };
};

export const getEntity: ICrudGetAction<ISystemEventsHistory> = id => {
  const requestUrl = `${apiUrl}/get-system-events-history-by-client-id/${id}`;
  return {
    type: ACTION_TYPES.FETCH_SYSTEMEVENTSHISTORY,
    payload: axios.get<ISystemEventsHistory>(requestUrl),
  };
};

export const createEntity: ICrudPutAction<ISystemEventsHistory> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_SYSTEMEVENTSHISTORY,
    payload: axios.post(apiUrl + '/create-system-events-history-by-client-id', cleanEntity(entity)),
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<ISystemEventsHistory> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_SYSTEMEVENTSHISTORY,
    payload: axios.put(apiUrl + '/update-system-events-history-by-client-id', cleanEntity(entity)),
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<ISystemEventsHistory> = id => async dispatch => {
  const requestUrl = `${apiUrl}/delete-system-events-history-by-client-id/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_SYSTEMEVENTSHISTORY,
    payload: axios.delete(requestUrl),
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET,
});
