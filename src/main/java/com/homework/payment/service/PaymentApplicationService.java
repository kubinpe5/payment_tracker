package com.homework.payment.service;

import com.homework.payment.repository.PaymentRecord;
import com.homework.payment.repository.PaymentRepository;
import org.apache.commons.lang3.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.event.ContextStartedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Optional;
import java.util.Scanner;
import java.util.regex.Pattern;

@Service
public class PaymentApplicationService {
    private static final Logger LOGGER = LoggerFactory.getLogger(PaymentApplicationService.class);
    private static final String INPUT_FILE_DELIMITER = " ";
    private static final String QUIT = "QUIT";

    private final ApplicationContext applicationContext;
    private final ApplicationArguments applicationArguments;
    private final PaymentRepository paymentRepository;

    public PaymentApplicationService(final ApplicationContext applicationContext, final ApplicationArguments applicationArguments, final PaymentRepository paymentRepository) {
        this.applicationContext = applicationContext;
        this.applicationArguments = applicationArguments;
        this.paymentRepository = paymentRepository;
        initializeDataset();
    }

    /**
     * Method will read input file from program arguments or use initial input file
     */
    public void initializeDataset() {
        String[] args = applicationArguments.getSourceArgs();
        if (!ArrayUtils.isEmpty(args)) {
            final String filename = args[0];
            initPaymentRepository(filename);
        } else {
            initPaymentRepository("src/main/resources/input.csv");
        }
    }

    @EventListener
    public void handleAppContextInitialized(ContextStartedEvent event) {
        waitForInput();
    }

    /**
     * Method for user additional input, after user types QUIT, than it closes the SpringApplication
     */
    private void waitForInput() {
        Scanner input = new Scanner(System.in);
        String confirm;
        do {
            System.out.println("Add additional input for payment in format XXX 123456.");
            confirm = input.nextLine();
            if (confirm != null) {
                final Optional<PaymentRecord> record = validatePaymentInputFromCmd(confirm);
                record.ifPresent(paymentRepository::addRecord);
            }
        } while (confirm != null && !confirm.equalsIgnoreCase(QUIT));
        SpringApplication.exit(applicationContext, () -> 0);
    }

    /**
     * Method will validate String input and if user makes mistake it will log an error message
     * @param input String input in format ABC XXXXX where XXXXX is BigDecimal number
     * @return Return the payment record only if it is valid
     */
    private Optional<PaymentRecord> validatePaymentInputFromCmd(final String input) {
        final Pattern currencyPattern = Pattern.compile("[A-Z]{3}");
        final String[] data = input.split(INPUT_FILE_DELIMITER);
        if (data.length == 2) {
            // Check match of three uppercase letters
            if (currencyPattern.matcher(data[0]).matches()) {
                try {
                    final PaymentRecord paymentRecord = new PaymentRecord(data[0], new BigDecimal(data[1]));
                    return Optional.of(paymentRecord);
                } catch (NumberFormatException e) {
                    LOGGER.error("The second argument must be a number.");
                }
            } else {
                LOGGER.error("The first three letters must be uppercase.");
            }
        } else if (!input.equalsIgnoreCase(QUIT)) {
            LOGGER.error("Input from user was not valid. Use the right pattern.");
        }
        return Optional.empty();
    }

    /**
     * Method for initialization of repository.
     * It will read the input file and fill the PaymentRepository with PaymentRecords provided in input file
     *
     * @param fileName Name of a file with input data
     */
    public void initPaymentRepository(final String fileName) {
        LOGGER.info("Reading file {}", fileName);
        File file = new File(fileName);
        try {
            if (file.exists()) {
                readFromInputFile(fileName);
                LOGGER.info("CSV file was successfully parsed");
            }
        } catch (IOException e) {
            LOGGER.warn(e.getMessage());
        }
    }

    /**
     * Method for reading and parsing file rows.
     * @param fileName filename with path on system
     * @throws IOException if the file reader fail to read the file
     */
    private void readFromInputFile(final String fileName) throws IOException {
        String line;
        BufferedReader bufReader = new BufferedReader(new FileReader(fileName));
        while ((line = bufReader.readLine()) != null) {
            Optional<PaymentRecord> record = validatePaymentInputFromCmd(line);
            record.ifPresent(paymentRepository::addRecord);
        }
        bufReader.close();
    }

    /**
     * Method for print payment record from the repository
     * @return Printed payment records with USD currency if available
     */
    public String printPaymentsRecords() {
        return paymentRepository.printRecords();
    }

}
