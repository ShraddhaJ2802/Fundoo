package com.bridgelab.funapp.exception;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.bridgelab.funapp.exception.LabelCustomeException;
import com.bridgelab.funapp.exception.UserCustomException;
import com.bridgelab.funapp.dto.ResponseDto;

//To handle all exception  global exception
@ControllerAdvice
public class HandleException {
	//which type of exception
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ResponseDto> handleMethodArgumentNotValidException (MethodArgumentNotValidException exception)
	{
		List<ObjectError> errorList = exception.getBindingResult().getAllErrors();
		List<String> errMsg = errorList.stream()
			 		.map(objectError -> objectError.getDefaultMessage())
			 		.collect(Collectors.toList());
		ResponseDto responseDto = new ResponseDto("Exception while processing REST Request",errMsg);
		return new ResponseEntity<ResponseDto>(responseDto, HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(FunToCustomException.class)
	public ResponseEntity<ResponseDto> handleNoterCustomException(FunToCustomException exception){
		ResponseDto responseDto = new ResponseDto("Exception while processing REST Request",exception.getMessage());
		return new ResponseEntity<ResponseDto>(responseDto, HttpStatus.BAD_REQUEST);
	   }

	@ExceptionHandler(UserCustomException.class)
	public ResponseEntity<ResponseDto> handleUserCustomException(UserCustomException exception)
	{
		ResponseDto responseDto = new ResponseDto("Exception while processing REST Request",exception.getMessage());
		return new ResponseEntity<ResponseDto>(responseDto, HttpStatus.BAD_REQUEST);
		}
	
	@ExceptionHandler(LabelCustomeException.class)
	public ResponseEntity<ResponseDto> handlLabelCustomException(LabelCustomeException exception)
	{
		ResponseDto responseDto = new ResponseDto("Exception while processing REST Request",exception.getMessage());
		return new ResponseEntity<ResponseDto>(responseDto, HttpStatus.BAD_REQUEST);
	   }
	
	@ExceptionHandler(CollabratorCustomException.class)
	public ResponseEntity<ResponseDto> handleCollabratorCustomException(CollabratorCustomException exception)
	{
		ResponseDto responseDto = new ResponseDto("Exception while processing REST Request",exception.getMessage());
		return new ResponseEntity<ResponseDto>(responseDto, HttpStatus.BAD_REQUEST);
		}
	
	@ExceptionHandler(EmailAlreadyExistsException.class)
    public ResponseEntity<String> handleEmailAlreadyExistsException(EmailAlreadyExistsException e) {
		ResponseDto responseDto = new ResponseDto("Exception while processing REST Request",e.getMessage());        
		return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
    }
}
