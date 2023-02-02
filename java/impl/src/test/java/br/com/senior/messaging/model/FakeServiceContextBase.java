package br.com.senior.messaging.model;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.function.Consumer;

import br.com.senior.messaging.Message;
import br.com.senior.messaging.Messenger;
import br.com.senior.messaging.configuration.Configurations;
import br.com.senior.messaging.configuration.ServiceConfiguration;

public class FakeServiceContextBase {

	public static final String TENANT = "seniortest";
	public static final String USERNAME = "admin";

	static Messenger messenger;

	static ServiceRunner mockServiceRunner;

	static ServiceConfiguration mockServiceConfiguration;

	static Thread executorThread;

	public static <T> void setUp() {
		setUp(TENANT, USERNAME);
	}

	@SuppressWarnings("unchecked")
	public static <T> void setUp(String tenant, String username) {
		mockServiceRunner = mock(ServiceRunner.class);

		mockServiceRunner.service = mock(Service.class);
		lenient().when(mockServiceRunner.getService()).thenReturn(mockServiceRunner.service);
		lenient().when(mockServiceRunner.service.getDomain()).thenReturn("DOMAIN");
		lenient().when(mockServiceRunner.service.getName()).thenReturn("SERVICE");
		Message mockMessage = mock(Message.class);
		lenient().when(mockMessage.containsHeader(Message.SELECTOR_HEADER)).thenReturn(true);
		lenient().when(mockMessage.getSelector()).thenReturn(tenant);

		lenient().when(mockMessage.containsHeader(Message.USERNAME_HEADER)).thenReturn(true);
		lenient().when(mockMessage.getUsername()).thenReturn(username);

		lenient().when(mockMessage.followUp(any(), any(), any(), any())).thenAnswer(invocation -> {
			Message message = mock(Message.class);
			when(message.getSelector()).thenReturn(tenant);
			when(message.getPrimitive()).thenReturn(invocation.getArgument(2));
			when(message.getContent()).thenReturn(invocation.getArgument(3));
			return message;
		});

		lenient().doAnswer(invocation -> {
			executorThread = new Thread(() -> {
				ServiceContext.get().setCurrentMessage(mockServiceRunner, mockMessage);
				Object[] args = invocation.getArguments();
				Consumer<Message> c = (Consumer<Message>) args[0];
				c.accept(mock(Message.class));
			});
			executorThread.start();
			return null;
		}).when(mockServiceRunner).runAsyncProcess(any(Runnable.class));

		lenient().doAnswer(invocation -> {
			Thread t1 = new Thread(() -> {
				ServiceContext.get().setCurrentMessage(mockServiceRunner, mockMessage);
				Object[] args = invocation.getArguments();
				Message message = (Message) args[0];
				Consumer<Message> c = (Consumer<Message>) args[1];
				c.accept(message);
			});
			t1.start();
			return null;
		}).when(mockServiceRunner).runAsyncProcess(any(), any());

		Configurations mockConfigurations = mock(Configurations.class);
		mockServiceConfiguration = mock(ServiceConfiguration.class);
		lenient().when(mockConfigurations.get()).thenReturn(mockServiceConfiguration);

		ServiceContext serviceContext = new ServiceContext(Arrays.asList(mockServiceRunner.service),
				(Service service) -> mockServiceRunner);
		serviceContext.setCurrentConfigurationsSupplier(() -> mockConfigurations);
		ServiceContext.install(serviceContext);
		ServiceContext.get().setCurrentMessage(mockServiceRunner, mockMessage);

	}

	public static Messenger getMessenger() {
		return messenger;
	}

	public static ServiceRunner getMockServiceRunner() {
		return mockServiceRunner;
	}

	public static ServiceConfiguration getServiceConfiguration() {
		return mockServiceConfiguration;
	}

	public static Thread getExecutorThread() {
		return executorThread;
	}

}
