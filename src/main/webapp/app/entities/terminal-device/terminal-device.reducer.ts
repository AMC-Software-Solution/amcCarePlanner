import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { ITerminalDevice, defaultValue } from 'app/shared/model/terminal-device.model';

export const ACTION_TYPES = {
  FETCH_TERMINALDEVICE_LIST: 'terminalDevice/FETCH_TERMINALDEVICE_LIST',
  FETCH_TERMINALDEVICE: 'terminalDevice/FETCH_TERMINALDEVICE',
  CREATE_TERMINALDEVICE: 'terminalDevice/CREATE_TERMINALDEVICE',
  UPDATE_TERMINALDEVICE: 'terminalDevice/UPDATE_TERMINALDEVICE',
  DELETE_TERMINALDEVICE: 'terminalDevice/DELETE_TERMINALDEVICE',
  RESET: 'terminalDevice/RESET',
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<ITerminalDevice>,
  entity: defaultValue,
  updating: false,
  totalItems: 0,
  updateSuccess: false,
};

export type TerminalDeviceState = Readonly<typeof initialState>;

// Reducer

export default (state: TerminalDeviceState = initialState, action): TerminalDeviceState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_TERMINALDEVICE_LIST):
    case REQUEST(ACTION_TYPES.FETCH_TERMINALDEVICE):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true,
      };
    case REQUEST(ACTION_TYPES.CREATE_TERMINALDEVICE):
    case REQUEST(ACTION_TYPES.UPDATE_TERMINALDEVICE):
    case REQUEST(ACTION_TYPES.DELETE_TERMINALDEVICE):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true,
      };
    case FAILURE(ACTION_TYPES.FETCH_TERMINALDEVICE_LIST):
    case FAILURE(ACTION_TYPES.FETCH_TERMINALDEVICE):
    case FAILURE(ACTION_TYPES.CREATE_TERMINALDEVICE):
    case FAILURE(ACTION_TYPES.UPDATE_TERMINALDEVICE):
    case FAILURE(ACTION_TYPES.DELETE_TERMINALDEVICE):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload,
      };
    case SUCCESS(ACTION_TYPES.FETCH_TERMINALDEVICE_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data,
        totalItems: parseInt(action.payload.headers['x-total-count'], 10),
      };
    case SUCCESS(ACTION_TYPES.FETCH_TERMINALDEVICE):
      return {
        ...state,
        loading: false,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.CREATE_TERMINALDEVICE):
    case SUCCESS(ACTION_TYPES.UPDATE_TERMINALDEVICE):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.DELETE_TERMINALDEVICE):
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

const apiUrl = 'api/terminal-devices';

// Actions

export const getEntities: ICrudGetAllAction<ITerminalDevice> = (page, size, sort) => {
  const requestUrl = `${apiUrl}${sort ? `?page=${page}&size=${size}&sort=${sort}` : ''}`;
  return {
    type: ACTION_TYPES.FETCH_TERMINALDEVICE_LIST,
    payload: axios.get<ITerminalDevice>(`${requestUrl}${sort ? '&' : '?'}cacheBuster=${new Date().getTime()}`),
  };
};

export const getEntity: ICrudGetAction<ITerminalDevice> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_TERMINALDEVICE,
    payload: axios.get<ITerminalDevice>(requestUrl),
  };
};

export const createEntity: ICrudPutAction<ITerminalDevice> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_TERMINALDEVICE,
    payload: axios.post(apiUrl, cleanEntity(entity)),
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<ITerminalDevice> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_TERMINALDEVICE,
    payload: axios.put(apiUrl, cleanEntity(entity)),
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<ITerminalDevice> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_TERMINALDEVICE,
    payload: axios.delete(requestUrl),
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET,
});
