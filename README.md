# Student Record Management System

A Java console application built to manage student records and demonstrate different methods of file handling (I/O) in Java. The project implements standard CRUD operations in memory and supports saving/loading data via plain text, binary streams, and object serialization.

## Features

- **Student CRUD:** Add, search, update, and delete student records dynamically.
- **Text Persistence:** Saves data as comma-separated values (CSV) using `PrintWriter` and parses them back using `Scanner`.
- **Binary Persistence:** Writes primitive data types directly as bytes using `DataOutputStream` and reads them back with `DataInputStream`.
- **Object Serialization:** Saves the entire student list state at once by implementing the `Serializable` interface and using `ObjectOutputStream`.
- **System Backups:** Generates timestamped backups of current records using `BufferedWriter`.
- **Reports:** Calculates total student count, highest/lowest GPAs, and class averages, and exports the summary to a text file.
- **File Metadata:** Inspects files natively using the `java.io.File` class to display absolute paths, file sizes in bytes, and last modified timestamps.

